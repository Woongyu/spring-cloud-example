package com.platform.member.service;

import com.platform.common.dto.enums.ErrorType;
import com.platform.common.exception.APIException;
import com.platform.member.dto.GradeResponse;
import com.platform.member.dto.enums.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {
    public Mono<GradeResponse> getAllGrades() {
        return Mono.fromCallable(this::getGrades)
            .subscribeOn(Schedulers.boundedElastic())
            .onErrorResume(throwable -> {
                log.error("Error: {}", throwable.getMessage());
                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to get grade info"));
            });
    }

    private GradeResponse getGrades() throws APIException {
        List<Grade> grades = Arrays.asList(Grade.values());
        if (grades.isEmpty()) {
            throw new APIException(ErrorType.DATA_NOT_FOUND);
        }

        List<GradeResponse.GradeInfo> gradeInfos = grades.stream()
            .map(grade -> GradeResponse.GradeInfo.builder()
                .tire(grade.getTier())
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
}
