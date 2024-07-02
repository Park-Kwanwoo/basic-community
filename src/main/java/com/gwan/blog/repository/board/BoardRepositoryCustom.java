package com.gwan.blog.repository.board;

import com.gwan.blog.domain.Board;
import com.gwan.blog.request.board.BoardPagination;
import com.gwan.blog.response.board.BoardUserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardRepositoryCustom {

    Page<Board> getList(BoardPagination boardPagination);

    BoardUserResponse getBoardAndUser(Long id);
}
