package com.gwan.blog.response.board;

import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardUserResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final String username;

    @QueryProjection
    public BoardUserResponse(Long id, String title, String content, LocalDateTime createdDate, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.username = username;
    }
}
