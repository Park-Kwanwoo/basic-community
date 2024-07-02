package com.gwan.blog.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        indexes = {
                @Index(name = "IDX_COMMENT_BOARD_ID", columnList = "board_id")
        }
)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn
    private Board board;

    public Comment(Long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    @Builder
    public Comment(String author, String password, String content) {
        this.author = author;
        this.password = password;
        this.content = content;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
