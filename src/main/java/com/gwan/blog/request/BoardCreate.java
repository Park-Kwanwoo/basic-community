package com.gwan.blog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank
    private String content;

    @Builder
    public BoardCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
