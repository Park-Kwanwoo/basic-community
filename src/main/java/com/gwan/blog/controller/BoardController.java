package com.gwan.blog.controller;

import com.gwan.blog.request.BoardCreate;
import com.gwan.blog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @PostMapping("/board")
    public void post(@RequestBody @Valid BoardCreate boardCreate) {
        boardService.write(boardCreate);
    }
}
