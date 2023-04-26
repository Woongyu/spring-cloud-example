package com.platform.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.enums.ErrorType;
import com.platform.common.exception.APIException;
import com.platform.member.dto.MemberDetail;
import com.platform.member.dto.PostResponse;
import com.platform.member.dto.UserActivityResponse;
import com.platform.member.entity.MemberEntity;
import com.platform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final WebClient webClient;
    private final MemberRepository memberRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${location.target-url.board}")
    private String boardUrl;

    public Mono<MemberDetail.Response> getMemberById(Integer userId) {
        return Mono.fromSupplier(() -> memberRepository.findByUserId(userId)
            .map(memberEntity -> {
                MemberDetail.Response response = new MemberDetail.Response();
                BeanUtils.copyProperties(memberEntity, response);
                return response;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO DATA")));
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

    public Mono<UserActivityResponse> getUserActivity(int userId) {
        Mono<PostResponse> postResponse = webClient.mutate()
            .baseUrl(boardUrl)
            .build()
            .get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/board/posts")
                .queryParam("user_id", userId)
                .build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new APIException(ErrorType.NO_DATA)))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new APIException(ErrorType.API_ERROR)))
            .bodyToMono(PostResponse.class)
            .onErrorResume(throwable -> {
                if (throwable instanceof WebClientResponseException) {
                    WebClientResponseException e = (WebClientResponseException) throwable;
                    if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new APIException(ErrorType.SERVER_ERROR));
                    }
                } else if (throwable instanceof APIException) {
                    return Mono.error(new APIException(ErrorType.NO_DATA));
                }

                return Mono.error(new RuntimeException(throwable.getMessage()));
            });

        return postResponse.flatMap(post -> {
            UserActivityResponse userActivityResponse = new UserActivityResponse();
            userActivityResponse.setUserId(userId);
            memberRepository.findByUserId(userId)
                .ifPresent(entity -> userActivityResponse.setUserName(entity.getUserName()));
            userActivityResponse.setPosts(post);
            return Mono.just(userActivityResponse);
        });
    }
}
