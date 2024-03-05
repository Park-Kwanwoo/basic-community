package com.gwan.blog.domain;

import com.gwan.blog.request.BoardCreate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public BoardEditor.BoardEditorBuilder toEditor() {
        return BoardEditor.builder()
                .title(this.title)
                .content(this.content);
    }

    public void edit(BoardEditor boardEditor) {
        this.title = boardEditor.getTitle();
        this.content = boardEditor.getContent();
    }
}
