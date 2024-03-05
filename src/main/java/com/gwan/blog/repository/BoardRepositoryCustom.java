package com.gwan.blog.repository;

import com.gwan.blog.domain.Board;
import com.gwan.blog.request.BoardPagination;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> getList(BoardPagination boardPagination);
}
