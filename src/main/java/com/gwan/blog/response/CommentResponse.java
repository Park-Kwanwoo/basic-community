package com.gwan.blog.response;

import com.gwan.blog.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final String author;
    private final String content;
    private final LocalDateTime createdDate;

    @Builder
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
    }
}
