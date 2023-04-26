package com.platform.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "PostResponse")
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {

    @JsonProperty("user_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer userId;

    @JsonProperty("post_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer postId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}
