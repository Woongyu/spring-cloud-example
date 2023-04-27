package com.platform.board.service;

import com.platform.board.dto.Post;
import com.platform.common.constant.Constant;
import com.platform.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        int postId = Constant.ONE;
        for (int userId = Constant.ONE; userId <= maxUserId; userId++) {
            List<Post.PostInfo> postInfos = new ArrayList<>();
            for (int localId = Constant.ONE; localId < CommonUtil.CommonRandom.nextInt(50) + 1; localId++, postId++) {
                Post.PostInfo postInfo = Post.PostInfo.builder()
                    .postId(postId)
                    .title(CommonUtil.generateTitle())
                    .content(CommonUtil.generateContent())
                    .likesCount(CommonUtil.CommonRandom.nextInt(150))
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
