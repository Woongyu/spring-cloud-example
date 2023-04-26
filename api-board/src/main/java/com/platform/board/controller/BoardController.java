package com.platform.board.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "게시판 정보", description = "Board API")
@Validated
@Slf4j
@RequestMapping("v1")
@RestController
@RequiredArgsConstructor
public class BoardController {

    @GetMapping(value = "/hello-world")
    public ResponseEntity<?> helloWorld() {
        Map<String, String> response = new HashMap<>();
        response.put("Hello", "World!");

        return ResponseEntity.ok(response);
    }
}
