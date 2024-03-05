package com.gwan.blog.response;

import com.gwan.blog.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class BoardListResponse {

    private final Long id;
    private final String title;

    public static BoardListResponse toBoardList(Board board) {

        return BoardListResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .build();
    }
}
