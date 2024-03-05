package com.gwan.blog.repository;

import com.gwan.blog.domain.Board;
import com.gwan.blog.domain.QBoard;
import com.gwan.blog.request.BoardPagination;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> getList(BoardPagination boardPagination) {
        return jpaQueryFactory.selectFrom(QBoard.board)
                .limit(boardPagination.getSize())
                .offset(boardPagination.getOffset())
                .orderBy(QBoard.board.id.desc())
                 .fetch();
    }
}
