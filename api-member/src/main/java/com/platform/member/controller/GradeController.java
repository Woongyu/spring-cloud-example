package com.platform.member.controller;

import com.platform.common.dto.BaseResponse;
import com.platform.member.dto.GradeResponse;
import com.platform.member.dto.MemberGrade;
import com.platform.member.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Tag(name = "등급 정보", description = "Grade API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @Operation(summary = "등급 정보 조회", description = "등급 정보 리스트를 조회합니다.")
    @GetMapping(value = "/grades",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GradeResponse> getGradeList() {
        log.info("Get grade info");
        return gradeService.getAllGrades();
    }

    @Operation(summary = "회원 등급 정보 수정",
        description = "(단건) 회원의 등급 정보를 수정합니다.")
    @PatchMapping("/member/{userId}/grade")
    public Mono<BaseResponse> updateGradeForMember(@Parameter(description = "User ID", example = "1", required = true)
                                                       @PathVariable(name = "userId") Integer userId,
                                                   @RequestBody @Valid MemberGrade.MemberInfo request){
        log.info("Update Grade userId [{}] request [{}]", userId, request.toString());
        return gradeService.updateGradeForMember(userId, request);
    }
}