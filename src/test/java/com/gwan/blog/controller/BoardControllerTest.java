package com.gwan.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwan.blog.config.BlogMockUser;
import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.User;
import com.gwan.blog.exception.BoardNotFound;
import com.gwan.blog.exception.UserNotFound;
import com.gwan.blog.repository.board.BoardRepository;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.request.board.BoardCreate;
import com.gwan.blog.request.board.BoardEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper obj;

    // 각 TEST가 실행되기 전 Repository 비우기
    @AfterEach
    void clean() {
        userRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @BlogMockUser
    @DisplayName("[CONTROLLER][POST][FAILED] - 새로운 글 작성 시 부적절한 요청으로 인한 실패 케이스")
    void POST_BOARD_INVALID_REQUEST() throws Exception {

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
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validationTuples[0].fieldName").value("title"))
                .andExpect(jsonPath("$.validationTuples[0].errorMessage").value("제목을 입력해주세요."))
                .andDo(print());

    }

    @Test
    @BlogMockUser
    @DisplayName("[CONTROLLER][POST][SUCCESS] - 새로운 글 작성시 성공 케이스")
    void POST_BOARD_VALID_REQUEST() throws Exception {

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

    @Test
    @DisplayName("[CONTROLLER][GET][SUCCESS] - 글 단건 조회에 대한 성공 케이스")
    void GET_BOARD() throws Exception {

        // given
        User user = User.builder()
                .email("gwan@blog.com")
                .password("1234")
                .name("관맨")
                .build();

        userRepository.save(user);

        Board board = Board.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();

        boardRepository.save(board);

        // expected
        mockMvc.perform(get("/board/{boardId}", board.getId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(board.getId()))
                .andExpect(jsonPath("$.title").value("제목입니다."))
                .andExpect(jsonPath("$.content").value("내용입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("[CONTROLLER][GET][SUCCESS] - 글 리스트 조회에 대한 성공 케이스")
    void GET_BOARD_LIST() throws Exception {

        // given
        User user = User.builder()
                .email("gwan@blog.com")
                .password("1234")
                .name("관맨")
                .build();

        userRepository.save(user);

        List<Board> boards = IntStream.range(1, 30)
                .mapToObj(i -> Board.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        boardRepository.saveAll(boards);

        // expected
        mockMvc.perform(get("/board")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(boards.getLast().getId()))
                .andExpect(jsonPath("$[0].title").value("제목29"))
                .andDo(print());
    }

    @Test
    @BlogMockUser
    @DisplayName("[CONTROLLER][PATCH][FAILED] - 글 수정 시 부적절한 요청으로 인한 실패 케이스")
    void PATCH_BOARD_INVALID_REQUEST() throws Exception {

        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        // given
        Board board = Board.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();

        boardRepository.save(board);

        BoardEdit boardEdit = BoardEdit.builder()
                .title("수정 후 제목")
                .build();

        String json = obj.writeValueAsString(boardEdit);

        // expected
        mockMvc.perform(patch("/board/{boardId}", board.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validationTuples[0].fieldName").value("content"))
                .andExpect(jsonPath("$.validationTuples[0].errorMessage").value("내용을 입력해주세요."))
                .andDo(print());

    }

    @Test
    @BlogMockUser
    @DisplayName("[CONTROLLER][PATCH][SUCCESS] - 글 수정에 대한 성공 케이스")
    void PATCH_BOARD_VALID_REQUEST() throws Exception {

        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        // given
        Board board = Board.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();

        boardRepository.save(board);

        BoardEdit boardEdit = BoardEdit.builder()
                .title("수정 후 제목")
                .content("내용입니다.")
                .build();

        String json = obj.writeValueAsString(boardEdit);

        // expected
        mockMvc.perform(patch("/board/{boardId}", board.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        Board changedBoard = boardRepository.findById(board.getId())
                .orElseThrow(BoardNotFound::new);

        assertEquals("수정 후 제목", changedBoard.getTitle());
        assertEquals("내용입니다.", changedBoard.getContent());
    }

    @Test
    @BlogMockUser
    @DisplayName("[CONTROLLER][DELETE][FAILED] - 글 삭제 시 존재하지 않는 글 삭제 실패 케이스")
    void DELETE_BOARD_NOT_EXIST() throws Exception {

        // expected
        mockMvc.perform(delete("/board/{boardId}", 1L)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지 않는 글입니다."))
                .andDo(print());
    }

    @Test
    @BlogMockUser
    @DisplayName("[CONTROLLER][DELETE][SUCCESS] - 글 삭제 성공 케이스")
    void DELETE_BOARD_EXIST() throws Exception {


        // given
        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        Board board = Board.builder()
                .user(user)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        boardRepository.save(board);

        // expected
        mockMvc.perform(delete("/board/{boardId}", board.getId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

