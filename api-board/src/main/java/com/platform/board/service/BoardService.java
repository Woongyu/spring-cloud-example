package com.platform.board.service;

import com.platform.board.dto.Post;
import com.platform.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final ConcurrentLinkedQueue<Post> postList = new ConcurrentLinkedQueue<>();

    @PostConstruct
    private void init() {
        createPost();
    }

    private void createPost() {
        final int maxUserId = 20;
        for (int userId = 1; userId <= maxUserId; userId++) {
            List<Post.PostInfo> postInfos = new ArrayList<>();
            for (int postId = 1; postId < CommonUtil.CommonRandom.nextInt(30) + 1; postId++) {
                Post.PostInfo postInfo = Post.PostInfo.builder()
                    .postId(postId)
                    .title(CommonUtil.generateTitle())
                    .content(CommonUtil.generateContent())
                    .likesCount(CommonUtil.CommonRandom.nextInt(100))
                    .build();

                postInfos.add(postInfo);
            }

            Post post = Post.builder()
                .userId(userId)
                .postCnt(postInfos.size())
                .postList(postInfos)
                .build();

            postList.add(post);
        }
    }

    public Post getPostsByUserId(int userId) {
        return postList.stream()
            .filter(post -> post.getUserId() == userId)
            .findFirst()
            .orElse(null);
    }
}
