package com.gwan.blog.controller;

import com.gwan.blog.request.BoardCreate;
import com.gwan.blog.request.BoardEdit;
import com.gwan.blog.request.BoardPagination;
import com.gwan.blog.response.BoardListResponse;
import com.gwan.blog.response.BoardResponse;
import com.gwan.blog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @PostMapping("/board")
    public void board(@RequestBody @Valid BoardCreate boardCreate) {
        boardService.write(boardCreate);
    }

    @GetMapping("/board/{boardId}")
    public BoardResponse get(@PathVariable Long boardId) {
        return boardService.get(boardId);
    }

    @GetMapping("/board")
    public List<BoardListResponse> getList(@ModelAttribute BoardPagination boardPagination) {
        return boardService.getList(boardPagination);
    }

    @PatchMapping("/board/{boardId}")
    public void edit(@PathVariable Long boardId,
                     @RequestBody @Valid BoardEdit boardEdit) {
        boardService.edit(boardId, boardEdit);
    }

    @DeleteMapping("/board/{boardId}")
    public void delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
    }
}
