package com.gwan.blog.service;

import com.gwan.blog.domain.User;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void test() {

        // given
        Signup signup = Signup.builder()
                .email("erangel@blog.com")
                .password("1234")
                .name("에랑겔")
                .build();

        // when
        userService.signup(signup);

        // then
        assertEquals(1L, userRepository.count());

        User findUser = userRepository.findAll().iterator().next();
        assertEquals("erangel@blog.com", findUser.getEmail());
        assertEquals("에랑겔", findUser.getName());

    }
}