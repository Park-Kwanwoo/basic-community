package com.gwan.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper obj;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void test() throws Exception {

        // given
        Signup signup = Signup.builder()
                .email("test@blog.com")
                .password("1234")
                .name("테스터박")
                .build();

        String json = obj.writeValueAsString(signup);

        // expected
        mockMvc.perform(post("/user/signup")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }
}