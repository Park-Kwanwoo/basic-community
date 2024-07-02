package com.gwan.blog.request.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDelete {

    private String password;

    @Builder
    public CommentDelete(String password) {
        this.password = password;
    }
}
