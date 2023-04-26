package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.member.dto.common.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

public class MemberDetail {

    @Schema(name = "DetailRequest")
    @Getter
    @ToString
    @NoArgsConstructor
    public static class Request {

        @NotNull
        @JsonProperty("user_id")
        private Integer userId;
    }

    @Schema(name = "DetailResponse")
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends BaseResponse {

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("user_age")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer userAge;

        @JsonProperty("country")
        private String country;
    }

    private MemberDetail() {
    }
}
