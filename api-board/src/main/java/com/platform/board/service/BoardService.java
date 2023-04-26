package com.platform.board.service;

import com.platform.board.dto.Post;
import com.platform.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
            for (int postId = 1; postId < CommonUtil.CommonRandom.nextInt(30) + 1; postId++) {
                Post post = Post.builder()
                    .userId(userId)
                    .postId(postId)
                    .title(CommonUtil.generateTitle())
                    .content(CommonUtil.generateContent())
                    .build();

                postList.add(post);
            }
        }
    }

    public Flux<Post> getPostsByUserId(int userId) {
        return Flux.fromIterable(postList)
            .filter(post -> post.getUserId() == userId);
    }
}
