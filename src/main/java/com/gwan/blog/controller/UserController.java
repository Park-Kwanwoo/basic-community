package com.gwan.blog.controller;

import com.gwan.blog.config.UserPrincipal;
import com.gwan.blog.request.Signup;
import com.gwan.blog.response.UserResponse;
import com.gwan.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public void singup(@RequestBody @Valid Signup signup) {

        userService.signup(signup);
    }

    @GetMapping("/api/users/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponse userResponse = userService.getUserProfile(userPrincipal.getUserId());
        return ResponseEntity.ok(userResponse);
    }
}
