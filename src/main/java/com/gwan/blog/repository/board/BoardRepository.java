package com.gwan.blog.repository.board;

import com.gwan.blog.domain.Board;
import com.gwan.blog.response.board.BoardUserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
