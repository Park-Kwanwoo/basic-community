package com.gwan.blog.controller;

import com.gwan.blog.request.comment.CommentCreate;
import com.gwan.blog.request.comment.CommentDelete;
import com.gwan.blog.request.comment.CommentPagination;
import com.gwan.blog.response.CommentResponse;
import com.gwan.blog.response.PagingResponse;
import com.gwan.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/board/{boardId}/comments")
    public void write(@PathVariable Long boardId, @RequestBody @Valid CommentCreate commentCreate) {
        commentService.write(boardId, commentCreate);
    }

    @GetMapping("/api/{boardId}/comments")
    public PagingResponse<CommentResponse> getCommentList(@PathVariable Long boardId, CommentPagination commentPagination) {
        return commentService.getCommentList(boardId, commentPagination);
    }

    @PostMapping("/api/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId, @RequestBody @Valid CommentDelete commentDelete) {
        commentService.delete(commentId, commentDelete);
    }
}
