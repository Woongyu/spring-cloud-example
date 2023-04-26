package com.platform.member.controller;

import com.platform.member.dto.MemberDetail;
import com.platform.member.dto.common.BaseResponse;
import com.platform.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Tag(name = "회원 정보", description = "Member API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 상세정보 조회", description = "회원의 상세정보를 조회합니다.")
    @GetMapping(value = "/members/{userId}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MemberDetail.Response> getMemberDetail(@PathVariable(name = "userId") Integer userId) {
        log.info("Detail userId [{}]", userId);

        return memberService.getMemberById(userId);
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @PostMapping(value = "/members",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseResponse> createMember(@RequestBody @Valid MemberDetail.Request request) {
        log.info("Create request [{}]", request.toString());

        return memberService.createMember(request);
    }

    @Operation(summary = "회원 전체 목록 조회", description = "회원 전체 목록을 조회하는 API입니다.")
    @GetMapping(value = "/members",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MemberDetail.Response> getMemberList() {
        log.info("Get all members");

        return memberService.getAllMembers();
    }
}
