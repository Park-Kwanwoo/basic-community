package com.gwan.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwan.blog.config.BlogMockUser;
import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.Comment;
import com.gwan.blog.domain.User;
import com.gwan.blog.exception.UserNotFound;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.repository.board.BoardRepository;
import com.gwan.blog.repository.comment.CommentRepository;
import com.gwan.blog.request.comment.CommentDelete;
import com.gwan.blog.request.comment.CommentCreate;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean() {
        boardRepository.deleteAll();
        commentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @BlogMockUser
    @DisplayName("댓글 작성")
    void test1() throws Exception {

        // given
        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        Board board = Board.builder()
                .user(user)
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();

        boardRepository.save(board);

        var request = CommentCreate.builder()
                .author("댓글맨1")
                .password("123456")
                .content("댓글입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/board/{boardId}/comments", board.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void DELETE_COMMENT() throws Exception {

        // given
        User user = User.builder()
                .email("gwan@blog.com")
                .password("1234")
                .name("관맨")
                .build();

        userRepository.save(user);

        Board board = Board.builder()
                .user(user)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        boardRepository.save(board);

        Comment comment = Comment.builder()
                .author("댓글러1")
                .content("댓글입니다.")
                .password("123456")
                .build();

        comment.setBoard(board);

        commentRepository.save(comment);

        CommentDelete commentDelete = CommentDelete.builder()
                .password("123456")
                .build();

        String json = objectMapper.writeValueAsString(commentDelete);

        // expected
        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }
}