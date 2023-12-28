package com.gwan.blog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
