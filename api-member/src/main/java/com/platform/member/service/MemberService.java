package com.platform.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.common.constant.CommonConstants;
import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.enums.ErrorType;
import com.platform.common.exception.APIException;
import com.platform.common.util.CommonUtil;
import com.platform.member.dto.MemberActivityResponse;
import com.platform.member.dto.MemberDetail;
import com.platform.member.dto.PostResponse;
import com.platform.member.dto.TotalPost;
import com.platform.member.entity.MemberEntity;
import com.platform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final WebClient webClient;
    private final MemberRepository memberRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${location.target-url.board}")
    private String boardUrl;

    @Value("${reactor.schedulers.defaultPoolSize}")
    private int defaultPoolSize;

    public Mono<MemberDetail.Response> getMemberById(Integer userId) {
        return Mono.fromSupplier(() -> memberRepository.findByUserId(userId)
            .map(memberEntity -> {
                MemberDetail.Response response = new MemberDetail.Response();
                BeanUtils.copyProperties(memberEntity, response);
                return response;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No data")));
    }

    public Mono<BaseResponse> createMember(MemberDetail.Request request) {
        MemberEntity entity = new MemberEntity();
        BeanUtils.copyProperties(request, entity);

        return Mono.fromSupplier(() -> memberRepository.save(entity))
            .flatMap(result -> {
                if (result > 0) {
                    BaseResponse response = new BaseResponse();
                    return Mono.just(response);
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save member"));
                }
            })
            .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
    }

    public Flux<MemberDetail.Response> getAllMembers() {
        return memberRepository.findAll()
            .map(entity -> {
                MemberDetail.Response response = new MemberDetail.Response();
                BeanUtils.copyProperties(entity, response);
                return response;
            })
            .onErrorResume(throwable -> {
                log.error("Error: {}", throwable.getMessage());
                return Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to get all members"));
            });
    }

    public Mono<MemberActivityResponse> geMemberActivity(int userId) {
        Mono<PostResponse> postResponse = webClient.mutate()
            .baseUrl(boardUrl)
            .build()
            .get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/board/posts")
                .queryParam(CommonConstants.USER_ID, userId)
                .queryParam(CommonConstants.LIMIT, CommonConstants.ZERO)
                .build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new APIException(ErrorType.DATA_NOT_FOUND)))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new APIException(ErrorType.API_ERROR)))
            .bodyToMono(PostResponse.class)
            .onErrorResume(throwable -> {
                if (throwable instanceof WebClientResponseException) {
                    WebClientResponseException e = (WebClientResponseException) throwable;
                    if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new APIException(ErrorType.SERVER_ERROR));
                    }
                } else if (throwable instanceof APIException) {
                    return Mono.error(new APIException(ErrorType.DATA_NOT_FOUND));
                }

                return Mono.error(new RuntimeException(throwable.getMessage()));
            });

        return postResponse.flatMap(post -> {
            MemberActivityResponse memberActivityResponse = new MemberActivityResponse();
            memberActivityResponse.setUserId(userId);
            memberRepository.findByUserId(userId)
                .ifPresent(entity -> memberActivityResponse.setUserName(entity.getUserName()));
            memberActivityResponse.setPosts(post);
            return Mono.just(memberActivityResponse);
        });
    }

    public Flux<PostResponse> getAllPosts() {
        ParallelFlux<PostResponse> parallelFlux = Flux.range(CommonConstants.ONE, memberRepository.findMaxUserId())
            .parallel(defaultPoolSize)
            .runOn(Schedulers.parallel())
            .flatMap(userId -> webClient.mutate()
                .baseUrl(boardUrl)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/api/board/posts")
                    .queryParam(CommonConstants.USER_ID, userId)
                    .queryParam(CommonConstants.LIMIT, CommonConstants.ZERO)
                    .build())
                .retrieve()
                .bodyToFlux(PostResponse.class)
                .onErrorResume(e -> getRspCodeAndMsg((WebClientResponseException) e, userId)));

        return parallelFlux.sequential();
    }

    private Mono<PostResponse> getRspCodeAndMsg(WebClientResponseException e, Integer userId) {
        ErrorType errorType = e.getStatusCode() == HttpStatus.NOT_FOUND
            ? ErrorType.DATA_NOT_FOUND
            : ErrorType.findByErrorType(e.getStatusCode().value());
        String responseBody = e.getResponseBodyAsString();
        String rspCode = errorType.getCode();
        String rspMsg = errorType.getMessage();

        try {
            Map<String, Object> resultMap = mapper.readValue(responseBody, CommonUtil.JsonTypeRef);

            if (resultMap.containsKey(CommonConstants.RSP_CODE)) {
                rspCode = (String) resultMap.get(CommonConstants.RSP_CODE);
            }
            if (resultMap.containsKey(CommonConstants.RSP_MSG)) {
                rspMsg = (String) resultMap.get(CommonConstants.RSP_MSG);
            }
        } catch (JsonProcessingException jsonException) {
            log.warn("Http error with no json body");
        }

        return Mono.just(new PostResponse(userId, rspCode, rspMsg));
    }

    public Mono<TotalPost> getBestPostByMember() {
        return this.getAllPosts().collectList()
            .flatMap(postResponses -> {
                TotalPost response = new TotalPost();
                List<TotalPost.PostSummary> postSummaries = new ArrayList<>();
                for (PostResponse postResponse : postResponses) {
                    List<PostResponse.PostInfo> postInfoList = postResponse.getPostList();
                    int postIndex = CommonConstants.MINUS_ONE;
                    if (!ObjectUtils.isEmpty(postInfoList)) {
                        postIndex = IntStream.range(0, postInfoList.size())
                            .reduce((i, j) -> postInfoList.get(i).getLikesCount() > postInfoList.get(j).getLikesCount() ? i : j)
                            .orElse(CommonConstants.MINUS_ONE);
                    }

                    postSummaries.add(buildPostSummary(postIndex, postResponse.getUserId(), postInfoList));
                }

                // Sorted in reverse order of likes count
                postSummaries.sort(Comparator.comparingInt(TotalPost.PostSummary::getLikesCount).reversed());
                response.setTotalPostCnt(postSummaries.size());
                response.setTotalList(postSummaries);

                return Mono.just(response);
            });
    }

    private TotalPost.PostSummary buildPostSummary(int postIndex, Integer userId, List<PostResponse.PostInfo> postInfoList) {
        TotalPost.PostSummary postSummary;
        if (postIndex >= 0 && postIndex < postInfoList.size()) {
            postSummary = TotalPost.PostSummary.builder()
                .userId(userId)
                .title(postInfoList.get(postIndex).getTitle())
                .likesCount(postInfoList.get(postIndex).getLikesCount())
                .build();
        } else {
            postSummary = TotalPost.PostSummary.builder()
                .userId(userId)
                .title("No data")
                .likesCount(CommonConstants.ZERO)
                .build();
        }

        return postSummary;
    }
}
