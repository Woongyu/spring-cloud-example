package com.platform.member.controller;

import com.platform.common.dto.BaseResponse;
import com.platform.member.dto.MemberDetail;
import com.platform.member.dto.PostResponse;
import com.platform.member.dto.UserActivityResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    @Operation(summary = "유저 활동 현황 조회", description = "특정 유저의 활동 현황을 조회합니다.")
    @GetMapping("/member/activity")
    public Mono<UserActivityResponse> getUserActivity(@RequestParam("user_id") int userId) {
        return memberService.getUserActivity(userId);
    }

    @Operation(summary = "멤버별 최고 인기 게시물 조회",
        description = "모든 유저에 대한 가장 좋아요를 많이 받은 최고의 게시물을 조회합니다.")
    @GetMapping("/members/best-post")
    public Flux<PostResponse> getBestPostByMember() {
        return memberService.getBestPostByMember();
    }
}
