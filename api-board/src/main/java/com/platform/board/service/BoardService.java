package com.platform.board.service;

import com.platform.board.dto.Post;
import com.platform.common.constant.CommonConstants;
import com.platform.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final ConcurrentLinkedQueue<Post> postList = new ConcurrentLinkedQueue<>();
    private final List<Integer> postIndexList = IntStream.rangeClosed(1, 500)
        .boxed()
        .collect(Collectors.toList());

    @PostConstruct
    private void init() {
        Collections.shuffle(postIndexList);
        createPost();
    }

    private void createPost() {
        final int maxUserId = 20;
        int postIndex = 0;
        for (int userId = CommonConstants.ONE; userId <= maxUserId; userId++) {
            List<Post.PostInfo> postInfos = new ArrayList<>();
            int numPosts = CommonUtil.CommonRandom.nextInt(25) + 1;
            for (int localId = CommonConstants.ONE; localId < numPosts; localId++, postIndex++) {
                int newPostId = postIndexList.get(postIndex);
                Post.PostInfo postInfo = Post.PostInfo.builder()
                    .postId(newPostId)
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

    public Post getPostsByUserId(int userId, String nextPage, Integer limit) {
        if (limit > 0) {
            List<Post> filteredPosts = postList.stream()
                .filter(post -> post.getUserId().equals(userId))
                .collect(Collectors.toList());

            return filteredPosts.stream()
                .findFirst()
                .map(post -> {
                    List<Post.PostInfo> localList = Optional.ofNullable(post.getPostList())
                        .map(list -> CommonUtil.pageOf(list, nextPage, limit))
                        .orElse(Collections.emptyList());

                    return Post.builder()
                        .userId(userId)
                        .postCnt(localList.size())
                        .postList(localList)
                        .build();
                })
                .orElse(null);
        }

        return postList.stream()
            .filter(post -> post.getUserId().equals(userId))
            .findAny()
            .orElse(null);
    }
}
