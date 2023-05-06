package com.platform.member.service;

import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.enums.ErrorType;
import com.platform.common.exception.APIException;
import com.platform.member.dto.GradeResponse;
import com.platform.member.dto.MemberGrade;
import com.platform.member.dto.enums.Grade;
import com.platform.member.entity.MemberEntity;
import com.platform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
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

    public Mono<BaseResponse> updateGradeForMember(MemberGrade.MemberInfo request) {
        Optional<MemberEntity> optionalMember = memberRepository.findByUserId(request.getUserId());
        if (optionalMember.isPresent()) {
            MemberEntity entity = optionalMember.get();
            entity.updateTier(request.getTier());
            return Mono.fromSupplier(() -> memberRepository.updateGrade(entity))
                .map(result -> {
                    if (result > 0) {
                        return new BaseResponse();
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save grade");
                    }
                })
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update grade for user")));
        } else {
            return Mono.error(new APIException(ErrorType.DATA_NOT_FOUND));
        }
    }
}
