package com.platform.member.service;

import com.platform.common.constant.CommonConstants;
import com.platform.common.dto.enums.ErrorType;
import com.platform.common.exception.APIException;
import com.platform.member.dto.GradeResponse;
import com.platform.member.dto.MemberGrade;
import com.platform.member.dto.enums.Grade;
import com.platform.member.entity.MemberEntity;
import com.platform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {
    private final MemberRepository memberRepository;

    public Mono<GradeResponse> getAllGrade() {
        return Mono.fromCallable(this::getGrade)
            .subscribeOn(Schedulers.boundedElastic())
            .onErrorResume(throwable -> {
                log.error("Error: {}", throwable.getMessage());
                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to get grade info"));
            });
    }

    private GradeResponse getGrade() throws APIException {
        List<Grade> gradeList = Arrays.asList(Grade.values());
        if (gradeList.isEmpty()) {
            throw new APIException(ErrorType.DATA_NOT_FOUND);
        }

        List<GradeResponse.GradeInfo> gradeInfos = gradeList.stream()
            .map(grade -> GradeResponse.GradeInfo.builder()
                .tier(grade.getTier())
                .minLikes(grade.getMinLikes())
                .maxLikes(grade.getMaxLikes())
                .name(grade.name())
                .build())
            .collect(Collectors.toList());

        GradeResponse response = new GradeResponse();
        response.setGradeCnt(gradeInfos.size());
        response.setGradeList(gradeInfos);

        return response;
    }

    public Mono<MemberGrade.GrdUpdRes> updateGradeForMember(MemberGrade.MemberInfo request) {
        MemberGrade memberGrade = new MemberGrade();
        memberGrade.setMemberCnt(CommonConstants.ONE);
        memberGrade.setMemberList(Collections.singletonList(request));

        return updateGradeForMembers(memberGrade)
            .flatMap(responses -> Mono.justOrEmpty(responses.stream()
                .findFirst()
                .orElse(null)));
    }

    public Mono<List<MemberGrade.GrdUpdRes>> updateGradeForMembers(MemberGrade request) {
        List<Mono<MemberGrade.GrdUpdRes>> responseList = request.getMemberList()
            .stream()
            .map(memberInfo -> {
                Integer userId = memberInfo.getUserId();
                Integer tier = memberInfo.getTier();
                if (ObjectUtils.anyNull(userId, tier)) {
                    return Mono.just(new MemberGrade.GrdUpdRes(ErrorType.INVALID_PARAMS, userId));
                }

                return memberRepository.findByUserId(userId)
                    .map(entity -> {
                        if (!entity.getTier().equals(tier)) {
                            Optional<MemberEntity> updatedEntity = entity.updateTier(tier);
                            return updatedEntity.map(memberEntity -> updateGrade(memberEntity, userId))
                                .orElseGet(() -> Mono.just(new MemberGrade.GrdUpdRes(userId, ErrorType.BAD_REQUEST.getCode(), "Invalid tier value")));
                        } else {
                            // When the tier is the same, events are skipped to improve performance
                            return Mono.just(new MemberGrade.GrdUpdRes(ErrorType.SUCCESS, userId));
                        }
                    })
                    .orElseGet(() -> Mono.just(new MemberGrade.GrdUpdRes(ErrorType.USER_NOT_FOUND, userId)));
            })
            .collect(Collectors.toList());

        return Mono.zip(responseList, responses -> Arrays.stream(responses)
            .map(response -> (MemberGrade.GrdUpdRes) response)
            .collect(Collectors.toList()));
    }

    private Mono<MemberGrade.GrdUpdRes> updateGrade(MemberEntity entity, Integer userId) {
        return Mono.fromSupplier(() -> memberRepository.updateGrade(entity))
            .map(result -> {
                if (result > 0) {
                    return new MemberGrade.GrdUpdRes(ErrorType.SUCCESS, userId);
                } else {
                    return new MemberGrade.GrdUpdRes(ErrorType.DATA_NOT_FOUND, userId);
                }
            })
            .onErrorResume(throwable -> {
                if (throwable instanceof HttpClientErrorException) {
                    return Mono.just(new MemberGrade.GrdUpdRes(ErrorType.BAD_REQUEST, userId));
                } else {
                    return Mono.just(new MemberGrade.GrdUpdRes(ErrorType.UNKNOWN_ERROR, userId));
                }
            });
    }
}
