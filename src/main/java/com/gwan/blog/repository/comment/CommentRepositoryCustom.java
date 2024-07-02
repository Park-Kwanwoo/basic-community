package com.gwan.blog.repository.comment;

import com.gwan.blog.domain.Comment;
import com.gwan.blog.request.comment.CommentPagination;
import com.gwan.blog.response.CommentResponse;
import org.springframework.data.domain.Page;

public interface CommentRepositoryCustom {

    Page<Comment> getCommentList(Long boardId, CommentPagination commentPagination);
}
