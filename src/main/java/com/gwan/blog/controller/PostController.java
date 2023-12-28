package com.gwan.blog.controller;

import com.gwan.blog.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PostController {

    @PostMapping("/posts")
    public String post(@Valid PostCreate postCreate) {

        return "Hello World";
    }
}
