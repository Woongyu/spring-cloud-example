package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.member.dto.common.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

public class MemberDetail {

    @Schema(name = "MemberRequest")
    @Getter
    @ToString
    @NoArgsConstructor
    public static class Request {

        @NotNull
        @JsonProperty("userNumber")
        private Integer userNumber;
    }

    @Schema(name = "MemberResponse")
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Response extends BaseResponse {

        @JsonProperty("userId")
        private String userId;

        @JsonProperty("userName")
        private String userName;
    }

    private MemberDetail() {
    }
}
