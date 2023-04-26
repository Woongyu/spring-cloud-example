package com.platform.member.controller;

import com.platform.member.dto.MemberDetail;
import com.platform.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "회원 정보", description = "Member API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 상세정보 조회")
    @GetMapping(value = "/members/{userId}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MemberDetail.Response> getMemberDetail(@PathVariable(name = "userId") Integer userId) {
        log.info("Detail userId [{}]", userId);

        return memberService.getMemberById(userId);
    }
}
