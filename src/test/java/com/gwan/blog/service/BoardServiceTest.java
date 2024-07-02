package com.gwan.blog.service;

import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.User;
import com.gwan.blog.exception.BoardNotFound;
import com.gwan.blog.repository.board.BoardRepository;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.request.board.BoardCreate;
import com.gwan.blog.request.board.BoardEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

    @BeforeEach
    void clean()  {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("[SERVICE][SUCCESS] - 글 작성 성공")
    void POST_SUCCESS_BOARD_CREATE() {

        var user = User.builder()
                .name("관맨")
                .password("1234")
                .email("gwan@blog.com")
                .build();


        var boardCreate = BoardCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        userRepository.save(user);

        boardService.write(user.getId(), boardCreate);

        assertEquals(1L, boardRepository.count());
        Board savedBoard = boardRepository.findAll().get(0);

        assertEquals("제목", savedBoard.getTitle());
        assertEquals("내용", savedBoard.getContent());
        assertEquals(user.getEmail(), savedBoard.getUser().getEmail() );

    }

    @Test
    @DisplayName("[SERVICE][FAILED] - 존재하지 않는 글 단건 조회 시 오류 발생")
    void GET_FAILED_IF_BOARD_NOT_EXIST() {

        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();

        boardRepository.save(board);

        // expected
        BoardNotFound boardNotFound = assertThrows(BoardNotFound.class, () -> boardService.get(board.getId() + 1));
        assertEquals("존재하지 않는 글입니다.", boardNotFound.getMessage());

    }

    @Test
    @DisplayName("[SERVICE][SUCCESS] - 글 단건 조회 성공")
    void GET_SUCCESS_IF_BOARD_EXIST() {

        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();

        boardRepository.save(board);

        // expected
        assertEquals("제목", board.getTitle());
        assertEquals("내용", board.getContent());

    }

    @Test
    @DisplayName("[SERVICE][FAILED] - 존재하지 않는 글 삭제 시 오류 발생")
    void DELETE_FAILED_IF_BOARD_NOT_EXIST() {

        // given
        Board board = Board.builder()
                .title("관우")
                .content("박")
                .build();

        boardRepository.save(board);

        // expected
        BoardNotFound boardNotFound = assertThrows(BoardNotFound.class, () -> boardService.delete(board.getId() + 1L));
        assertEquals("존재하지 않는 글입니다.", boardNotFound.getMessage());

    }

    @Test
    @DisplayName("[SERVICE][SUCCESS] - 글 삭제 성공")
    void DELETE_SUCCESS_IF_BOARD_NOT_EXIST() {

        // given
        Board board = Board.builder()
                .title("관우")
                .content("박")
                .build();

        boardRepository.save(board);

        // when
        boardService.delete(board.getId());

        // then
        assertEquals(0, boardRepository.count());
        assertThrows(BoardNotFound.class, () -> boardService.get(board.getId()));

    }

    @Test
    @DisplayName("[SERVICE][FAILED] - 글 수정 시 존재하지 않는 글 수정 시 오류 발생")
    void PATCH_FAILED_IF_BOARD_NOT_EXIST() {

        // given
        Board board = Board.builder()
                .title("관우")
                .content("박")
                .build();

        BoardEdit boardEdit = BoardEdit.builder()
                .title(null)
                .content("벅")
                .build();

        boardRepository.save(board);

        // expected
        BoardNotFound boardNotFound = assertThrows(BoardNotFound.class, () -> boardService.edit(board.getId() + 1L, boardEdit));
        assertEquals("존재하지 않는 글입니다.", boardNotFound.getMessage());

    }

    @Test
    @DisplayName("[SERVICE][SUCCESS] - 글 수정 성공")
    void PATCH_SUCCESS_IF_BOARD_EXIST() {

        // given
        Board board = Board.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        BoardEdit boardEdit = BoardEdit.builder()
                .content("수정된 내용입니다.")
                .build();

        boardRepository.save(board);

        // when
        boardService.edit(board.getId(), boardEdit);

        // expected
        Board changedBoard = boardRepository.findById(board.getId())
                .orElseThrow(BoardNotFound::new);

        assertEquals(board.getId(), changedBoard.getId());
        assertEquals("제목입니다.", changedBoard.getTitle());
        assertEquals("수정된 내용입니다.", changedBoard.getContent());
    }
}