package com.gwan.blog.response;

import com.gwan.blog.domain.Board;
import lombok.*;

@RequiredArgsConstructor
@Builder
@Getter
public class BoardResponse {

    private final Long id;
    private final String title;
    private final String content;

    public static BoardResponse toBoardResponse(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
