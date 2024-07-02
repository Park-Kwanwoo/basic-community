package com.gwan.blog.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwan.blog.config.BlogMockUser;
import com.gwan.blog.domain.Board;
import com.gwan.blog.exception.UserNotFound;
import com.gwan.blog.repository.board.BoardRepository;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.request.board.BoardCreate;
import com.gwan.blog.request.board.BoardEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class BoardControllerSwaggerRestDocTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper obj;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
        boardRepository.deleteAll();

    }

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @BlogMockUser
    @DisplayName("게시글 작성")
    void CREATE_BOARD() throws Exception {

        // given
        BoardCreate board = BoardCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String data = obj.writeValueAsString(board);
//        obj.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        mockMvc.perform(post("/board")
                        .contentType(APPLICATION_JSON)
                        .content(data)
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("v1-create-board", resource(ResourceSnippetParameters.builder()
                        .description("게시글 생성")
                        .tag("board")
                        .requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        )
                        .requestSchema(schema("BoardCreate"))
                        .build())));
    }

    @Test
    @BlogMockUser
    @DisplayName("게시글 단건 조회")
    void GET_BOARD() throws Exception {

        // given
        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        Board board = Board.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();

        boardRepository.save(board);

        // expected
        mockMvc.perform(get("/board/{boardId}", board.getId())
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("v1-get-board",
                        resource(ResourceSnippetParameters.builder()
                                .tag("board")
                                .description("게시글 단건 조회")
                                .pathParameters(
                                        parameterWithName("boardId").description("조회할 게시글 번호"))
                                .responseFields(
                                        fieldWithPath("id").description("게시글 번호"),
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용")
                                )
                                .responseSchema(schema("BoardResponse"))
                                .build())))
        ;
    }

    @Test
    @DisplayName("게시글 리스트 조회")
    void GET_BOARD_LIST() throws Exception {

        // expected
        mockMvc.perform(get("/board")
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(document("v1-get-board-list",
                        resource(ResourceSnippetParameters.builder()
                                .tag("board")
                                .description("게시글 리스트 조회")
                                .responseFields(
                                        List.of()
                                )
                                .responseSchema(schema("BoardListResponse"))
                                .build())));
    }

    @Test
    @BlogMockUser
    @DisplayName("게시글 수정")
    void EDIT_BOARD() throws Exception {

        // given
        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        Board board = Board.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .user(user)
                .build();

        boardRepository.save(board);

        BoardEdit boardEdit = BoardEdit.builder()
                .title(board.getTitle())
                .content("수정 후 제목")
                .build();

        String editData = obj.writeValueAsString(boardEdit);
//        obj.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        mockMvc.perform(patch("/board/{boardId}", board.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(editData))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("v1-edit-board", resource(ResourceSnippetParameters.builder()
                        .tag("board")
                        .description("게시글 수정")
                        .pathParameters(
                                parameterWithName("boardId").description("게시글 번호")
                        )
                        .requestFields(
                                fieldWithPath("title").description("수정 할 제목"),
                                fieldWithPath("content").description("수정 할 내용")
                        )
                        .requestSchema(schema("BoardEdit"))
                        .build())));
    }

    @Test
    @BlogMockUser
    @DisplayName("게시글 삭제")
    void DELETE_BOARD() throws Exception {

        var user = userRepository.findByEmail("gwan@blog.com")
                .orElseThrow(UserNotFound::new);

        // given
        Board board = Board.builder()
                .user(user)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        boardRepository.save(board);

        // expected
        mockMvc.perform(delete("/board/{boardId}", board.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("v1-delete-board", resource(ResourceSnippetParameters.builder()
                        .tag("board")
                        .description("게시글 삭제")
                        .pathParameters(
                                parameterWithName("boardId").description("게시글 번호")
                        )
                        .build())));
    }
}
