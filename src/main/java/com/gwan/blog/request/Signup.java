package com.gwan.blog.request;

import com.gwan.blog.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class Signup {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이름을 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String email;

    @Builder
    public Signup(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User toUser() {

        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .build();
    }
}
