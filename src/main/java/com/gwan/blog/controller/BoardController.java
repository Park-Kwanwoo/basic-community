package com.gwan.blog.controller;

import com.gwan.blog.config.UserPrincipal;
import com.gwan.blog.request.board.BoardCreate;
import com.gwan.blog.request.board.BoardEdit;
import com.gwan.blog.request.board.BoardPagination;
import com.gwan.blog.response.board.BoardListResponse;
import com.gwan.blog.response.board.BoardUserResponse;
import com.gwan.blog.response.PagingResponse;
import com.gwan.blog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/board")
    public void board(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid BoardCreate boardCreate) {
        boardService.write(userPrincipal.getUserId(), boardCreate);
    }

    @GetMapping("/api/board/{boardId}")
    public BoardUserResponse get(@PathVariable Long boardId) {
        return boardService.get(boardId);
    }

    @GetMapping("/api/board")
    public PagingResponse<BoardListResponse> getList(@ModelAttribute BoardPagination boardPagination) {
        return boardService.getList(boardPagination);
    }

    @PatchMapping("/api/board/{boardId}")
    public void edit(@PathVariable Long boardId,
                     @RequestBody @Valid BoardEdit boardEdit) {
        boardService.edit(boardId, boardEdit);
    }

    @DeleteMapping("/api/board/{boardId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#boardId, 'POST', 'DELETE')")
    public void delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
    }
}
