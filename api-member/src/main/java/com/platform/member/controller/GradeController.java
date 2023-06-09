package com.platform.member.controller;

import com.platform.member.dto.GradeResponse;
import com.platform.member.dto.MemberGrade;
import com.platform.member.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "등급 정보", description = "Grade API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @Operation(summary = "등급 정보 조회", description = "등급 정보 리스트를 조회합니다.")
    @GetMapping(value = "/grade",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GradeResponse> getGradeList() {
        log.info("Get grade info");
        return gradeService.getAllGrade();
    }

    @Operation(summary = "회원 등급 정보 수정 - 단건",
        description = "단일 회원의 등급 정보를 수정합니다.")
    @PatchMapping("/member/grade")
    public Mono<MemberGrade.GrdUpdRes> updateGradeForMember(@RequestBody @Valid MemberGrade.MemberInfo request){
        log.info("Update Member Grade request [{}]", request.toString());
        return gradeService.updateGradeForMember(request);
    }

    @Operation(summary = "회원 등급 정보 수정 - 다건",
        description = "다수 회원의 등급 정보를 수정합니다.")
    @PatchMapping("/members/grade")
    public Mono<List<MemberGrade.GrdUpdRes>> updateGradeForMembers(@RequestBody @Valid MemberGrade request){
        log.info("Update Members Grade request [{}]", request.toString());
        return gradeService.updateGradeForMembers(request);
    }
}
