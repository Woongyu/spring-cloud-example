package com.platform.member.controller;

import com.platform.common.constant.CommonConstants;
import com.platform.common.dto.BaseResponse;
import com.platform.member.dto.MemberDetail;
import com.platform.member.dto.PostResponse;
import com.platform.member.dto.MemberActivityResponse;
import com.platform.member.dto.TotalPost;
import com.platform.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @GetMapping(value = "/member/{userId}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MemberDetail.Response> getMemberDetail(@Parameter(description = "User ID", example = "1", required = true)
                                                       @PathVariable(name = "userId") Integer userId) {
        log.info("Detail userId [{}]", userId);
        return memberService.getMemberById(userId);
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @PostMapping(value = "/member",
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

    @Operation(summary = "회원 활동 현황 조회", description = "특정 회원의 활동 현황을 조회합니다.")
    @GetMapping("/member/activity")
    public Mono<MemberActivityResponse> geMemberActivity(@RequestParam(name = CommonConstants.USER_ID, defaultValue = "1") int userId) {
        log.info("Activity userId [{}]", userId);
        return memberService.geMemberActivity(userId);
    }

    @Operation(summary = "회원별 모든 게시물 조회",
        description = "모든 회원에 대한 게시물 전체를 조회합니다.")
    @GetMapping("/members/posts")
    public Flux<PostResponse> getPostList() {
        log.info("Get all posts from all members");
        return memberService.getAllPosts();
    }

    @Operation(summary = "회원별 최고 인기 게시물 조회",
        description = "모든 회원에 대한 가장 좋아요를 많이 받은 최고의 게시물을 조회합니다.")
    @GetMapping("/members/posts/best")
    public Mono<TotalPost> getBestPostByMember() {
        log.info("Get best posts from all members");
        return memberService.getBestPostByMember();
    }
}
