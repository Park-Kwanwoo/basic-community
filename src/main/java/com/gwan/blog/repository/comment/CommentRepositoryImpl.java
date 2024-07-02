package com.gwan.blog.repository.comment;

import com.gwan.blog.domain.Comment;
import com.gwan.blog.request.comment.CommentPagination;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.gwan.blog.domain.QBoard.board;
import static com.gwan.blog.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Comment> getCommentList(Long boardId, CommentPagination commentPagination) {

        long totalCount = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .fetchFirst();


        List<Comment> items = jpaQueryFactory.selectFrom(comment)
                .limit(commentPagination.getSize())
                .offset(commentPagination.getOffset())
                .orderBy(comment.id.desc())
                .where(board.id.eq(boardId))
                .fetch();

        return new PageImpl<>(items, commentPagination.getPageable(), totalCount);
    }
}
