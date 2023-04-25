package com.platform.member.controller;

import com.platform.member.dto.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "회원 정보", description = "Member API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class MamberController {

    @Operation(summary = "회원 추가정보 조회")
    @PostMapping(value = "/user-detail")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "202", description = "Accepted"),
                    @ApiResponse(responseCode = "302", description = "Found"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            })
    public ResponseEntity<MemberDetail.Response> memberDetail(
            @RequestBody @Valid MemberDetail.Request request) {
        log.info("memberDetail request [{}]", request.toString());

        HttpStatus status = HttpStatus.OK;

        MemberDetail.Response response = new MemberDetail.Response();
        response.setUserId("test");
        response.setUserName("GilDong Hong");

        return ResponseEntity.status(status)
                .body(response);
    }
}
