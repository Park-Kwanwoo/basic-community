package com.gwan.blog.repository.board;

import com.gwan.blog.domain.Board;
import com.gwan.blog.request.board.BoardPagination;
import com.gwan.blog.response.board.BoardUserResponse;
import com.gwan.blog.response.board.QBoardUserResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.gwan.blog.domain.QBoard.board;
import static com.gwan.blog.domain.QUser.user;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Board> getList(BoardPagination boardPagination) {

        long totalCount = jpaQueryFactory.select(board.count())
                .from(board)
                .fetchFirst();

        List<Board> items = jpaQueryFactory.selectFrom(board)
                .limit(boardPagination.getSize())
                .offset(boardPagination.getOffset())
                .orderBy(board.id.desc())
                .fetch();


        return new PageImpl<>(items, boardPagination.getPageable(), totalCount);
    }

    @Override
    public BoardUserResponse getBoardAndUser(Long id) {

        return jpaQueryFactory
                .select(new QBoardUserResponse(
                        board.id,
                        board.title,
                        board.content,
                        board.createdDate,
                        user.name))
                .from(board)
                .join(board.user, user)
                .where(board.id.eq(id))
                .fetchFirst();
    }
}
