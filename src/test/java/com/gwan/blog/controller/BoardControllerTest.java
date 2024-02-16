package com.gwan.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwan.blog.domain.Board;
import com.gwan.blog.repository.BoardRepository;
import com.gwan.blog.request.BoardCreate;
import com.gwan.blog.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ObjectMapper obj;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("[METHOD] POST / [CASE] Invalid Board Data")
    void INVALID_REQUEST() throws Exception {

        // given
        BoardCreate boardCreate = BoardCreate.builder()
                .content("내용")
                .build();

        String jsonData = obj.writeValueAsString(boardCreate);

        // expected
        mockMvc.perform(post("/board")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jsonData)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("오류 발생"))
                .andExpect(jsonPath("$.validationTuples[0].fieldName").value("title"))
                .andExpect(jsonPath("$.validationTuples[0].errorMessage").value("타이틀을 입력해주세요."))
                .andDo(print());

    }

    @Test
    @DisplayName("[METHOD] POST / [CASE] Create New Board")
    void VALID_REQUEST() throws Exception {

        // given
        BoardCreate boardCreate = BoardCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String jsonData = obj.writeValueAsString(boardCreate);


        // expected
        mockMvc.perform(post("/board")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jsonData)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Board board = boardRepository.findAll().get(0);

        assertEquals(1L, boardRepository.count());
        assertEquals("제목", board.getTitle());
        assertEquals("내용", board.getContent());

    }
}