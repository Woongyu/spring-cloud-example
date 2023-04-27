package com.platform.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "MemberActivityResponse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberActivityResponse extends BaseResponse {

    @JsonProperty("user_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("recent_post_count")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer recentPostCount;

    @JsonProperty("total_likes_count")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer totalLikesCount;

    public void setPosts(PostResponse posts) {
        this.recentPostCount = posts.getPostList().size();
        this.totalLikesCount = posts.getPostList()
            .stream()
            .mapToInt(PostResponse.PostInfo::getLikesCount)
            .sum();
    }
}
