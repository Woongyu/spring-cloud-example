package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name = "PostResponse")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse extends BaseResponse {

    @JsonProperty("user_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer userId;

    @JsonProperty("post_cnt")
    private String postCnt;

    @JsonProperty("post_list")
    private List<PostInfo> postList;

    public PostResponse(String rspCode, String rspMsg) {
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PostInfo {

        @JsonProperty("post_id")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer postId;

        @JsonProperty("title")
        private String title;

        @JsonProperty("content")
        private String content;

        @JsonProperty("likes_count")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Integer likesCount;
    }
}
