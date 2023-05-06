package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name = "GradeResponse")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeResponse extends BaseResponse {

    @JsonProperty("grade_cnt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer gradeCnt;

    @JsonProperty("grade_list")
    private List<GradeInfo> gradeList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GradeInfo {

        @JsonProperty("tire")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer tire;

        @JsonProperty("min_likes")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer minLikes;

        @JsonProperty("max_likes")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer maxLikes;

        @JsonProperty("name")
        private String name;
    }
}
