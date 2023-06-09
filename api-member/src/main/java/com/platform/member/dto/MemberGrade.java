package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.enums.ErrorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "MembersGrade")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberGrade {

    @Schema(description = "Member Cnt", defaultValue = "1")
    @JsonProperty("member_cnt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer memberCnt;

    @NotNull
    @Schema(description = "Member List", nullable = false)
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

    @Schema(name = "MemberGradeUpdateResponse")
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GrdUpdRes extends BaseResponse {

        @JsonProperty("user_id")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer userId;

        public GrdUpdRes(ErrorType errorType, Integer userId) {
            super(errorType);
            this.userId = userId;
        }

        public GrdUpdRes(Integer userId, String rspCode, String rspMsg) {
            this.userId = userId;
            this.rspCode = rspCode;
            this.rspMsg = rspMsg;
        }
    }
}
