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

@Schema(name = "TotalPost")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalPost extends BaseResponse {

    @JsonProperty("total_post_cnt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer totalPostCnt;

    @JsonProperty("total_list")
    private List<PostSummary> totalList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PostSummary {

        @JsonProperty("user_id")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer userId;

        @JsonProperty("title")
        private String title;

        @JsonProperty("likes_count")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer likesCount;
    }
}
