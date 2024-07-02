package com.gwan.blog.service;

import com.gwan.blog.domain.Comment;
import com.gwan.blog.exception.BoardNotFound;
import com.gwan.blog.exception.CommentNotFound;
import com.gwan.blog.repository.board.BoardRepository;
import com.gwan.blog.repository.comment.CommentRepository;
import com.gwan.blog.request.comment.CommentCreate;
import com.gwan.blog.request.comment.CommentDelete;
import com.gwan.blog.request.comment.CommentPagination;
import com.gwan.blog.response.CommentResponse;
import com.gwan.blog.response.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void write(Long boardId, CommentCreate commentCreate) {

        var board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFound::new);

        Comment comment = Comment.builder()
                .author(commentCreate.getAuthor())
                .password(commentCreate.getPassword())
                .content(commentCreate.getContent())
                .build();

        board.addComment(comment);

    }

    public void delete(Long commentId, CommentDelete commentDelete) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        if (comment.getPassword().equals(commentDelete.getPassword())) {
            commentRepository.delete(comment);
        }
    }

    public PagingResponse<CommentResponse> getCommentList(Long boardId, CommentPagination commentPagination) {

        Page<Comment> commentPage = commentRepository.getCommentList(boardId, commentPagination);

        return new PagingResponse<>(commentPage, CommentResponse.class);
    }
}
