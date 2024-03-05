package com.gwan.blog.domain;

import lombok.Getter;

@Getter
public class BoardEditor {

    private final String title;
    private final String content;

    public BoardEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static BoardEditorBuilder builder() {
        return new BoardEditorBuilder();
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public static class BoardEditorBuilder {
        private String title;
        private String content;

        BoardEditorBuilder() {
        }

        public BoardEditorBuilder title(final String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        public BoardEditorBuilder content(final String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        public BoardEditor build() {
            return new BoardEditor(this.title, this.content);
        }
    }
}