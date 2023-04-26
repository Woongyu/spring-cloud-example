package com.platform.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name = "Post")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Post {

    @JsonProperty("user_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer userId;

    @JsonProperty("post_cnt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer postCnt;

    @JsonProperty("post_list")
    private List<PostInfo> postList;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
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
