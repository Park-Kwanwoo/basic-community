package com.gwan.blog.response.board;

import com.gwan.blog.domain.Board;
import lombok.Getter;

@Getter
public class BoardListResponse {

    private final Long id;
    private final String title;
    private final String content;

    public BoardListResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
