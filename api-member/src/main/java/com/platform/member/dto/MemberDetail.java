package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

public class MemberDetail {

    @Schema(name = "DetailRequest")
    @Getter
    @Setter
    @ToString
    public static class Request {

        private static final String DEFAULT_COUNTRY = "South Korea";

        @NotNull
        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("user_age")
        private Integer userAge;

        @JsonProperty("country")
        private String country;

        public Request() {
            this.country = DEFAULT_COUNTRY;
        }
    }

    @Schema(name = "DetailResponse")
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends BaseResponse {

        @JsonProperty("user_id")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer userId;

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
