package com.platform.board.controller;

import com.platform.board.dto.Post;
import com.platform.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "게시판 정보", description = "Board API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/api/board/posts")
    public Mono<ResponseEntity<List<Post>>> getPostsByUserId(@RequestParam("user_id") int userId) {
        return boardService.getPostsByUserId(userId)
            .collectList()
            .map(posts -> ResponseEntity.ok(posts))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
