package com.gwan.blog.service;

import com.gwan.blog.domain.Board;
import com.gwan.blog.repository.BoardRepository;
import com.gwan.blog.request.BoardCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(BoardCreate boardCreate) {

        Board board = Board.builder()
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .build();

        boardRepository.save(board);
    }
}
