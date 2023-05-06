package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "MemberGrades")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberGrade extends BaseResponse {

    @JsonProperty("member_cnt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer memberCnt;

    @JsonProperty("member_list")
    private List<MemberInfo> memberList;

    @Schema(name = "MemberGrade")
    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MemberInfo {

        @NotNull
        @Schema(description = "User Id", defaultValue = "1", nullable = false)
        @JsonProperty("user_id")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer userId;

        @NotNull
        @Schema(description = "Tier", defaultValue = "1", nullable = false)
        @JsonProperty("tier")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer tier;
    }
}
