package com.gwan.blog.service;

import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.BoardEditor;
import com.gwan.blog.exception.BoardNotFound;
import com.gwan.blog.repository.BoardRepository;
import com.gwan.blog.request.BoardCreate;
import com.gwan.blog.request.BoardEdit;
import com.gwan.blog.request.BoardPagination;
import com.gwan.blog.response.BoardListResponse;
import com.gwan.blog.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(BoardCreate boardCreate) {

        Board board = BoardCreate.toEntity(boardCreate);

        boardRepository.save(board);
    }

    public BoardResponse get(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFound::new);

        return BoardResponse.toBoardResponse(board);
    }

    public List<BoardListResponse> getList(BoardPagination boardPagination) {

        return boardRepository.getList(boardPagination).stream()
                .map(BoardListResponse::toBoardList)
                .collect(Collectors.toList());
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
