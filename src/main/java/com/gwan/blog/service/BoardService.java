package com.gwan.blog.service;

import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.BoardEditor;
import com.gwan.blog.exception.BoardNotFound;
import com.gwan.blog.exception.UserNotFound;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.repository.board.BoardRepository;
import com.gwan.blog.request.board.BoardCreate;
import com.gwan.blog.request.board.BoardEdit;
import com.gwan.blog.request.board.BoardPagination;
import com.gwan.blog.response.board.BoardListResponse;
import com.gwan.blog.response.board.BoardUserResponse;
import com.gwan.blog.response.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void write(Long userId, BoardCreate boardCreate) {

        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Board.BoardBuilder boardBuilder = BoardCreate.toEntity(boardCreate);
        Board board = boardBuilder
                .user(user)
                .build();

        boardRepository.save(board);
    }

    public BoardUserResponse get(Long id) {

        return boardRepository.getBoardAndUser(id);
    }

    public PagingResponse<BoardListResponse> getList(BoardPagination boardPagination) {
        Page<Board> boardPage = boardRepository.getList(boardPagination);

        return new PagingResponse<>(boardPage, BoardListResponse.class);
    }

    @Transactional
    public void edit(Long id, BoardEdit boardEdit) {

        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFound::new);

        BoardEditor.BoardEditorBuilder editorBuilder = board.toEditor();

        BoardEditor boardEditor = editorBuilder.title(boardEdit.getTitle())
                .content(boardEdit.getContent())
                .build();

        board.edit(boardEditor);
    }

    public void delete(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFound::new);

        boardRepository.delete(board);

    }
}
