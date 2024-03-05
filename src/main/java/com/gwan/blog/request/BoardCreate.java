package com.gwan.blog.request;

import com.gwan.blog.domain.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreate {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public BoardCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Board toEntity(BoardCreate boardCreate) {
        return Board.builder()
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .build();
    }
}
