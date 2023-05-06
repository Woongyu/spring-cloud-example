package com.platform.board.controller;

import com.platform.board.dto.Post;
import com.platform.board.service.BoardService;
import com.platform.common.constant.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Tag(name = "게시판 정보", description = "Board API")
@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "유저별 게시물 조회", description = "유저별 게시물을 조회합니다.")
    @GetMapping("/api/board/posts")
    public ResponseEntity<Post> getPostsByUserId(@RequestParam(name = CommonConstants.USER_ID, defaultValue = "1") @NotNull int userId,
                                                 @RequestParam(name = CommonConstants.NEXT_PAGE, required = false) String nextPage,
                                                 @RequestParam(name = CommonConstants.LIMIT, defaultValue = "0") @NotNull Integer limit) {
        log.info("Posts userId [{}]", userId);
        return Optional.ofNullable(boardService.getPostsByUserId(userId, nextPage, limit))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
