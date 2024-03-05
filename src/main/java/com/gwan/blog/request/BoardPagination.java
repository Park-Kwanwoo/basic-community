package com.gwan.blog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPagination {

    private int page;

    private int size;

    public long getOffset() {
        return (long) (this.page - 1) * this.size;
    }

    public BoardPagination() {
        this.page = 1;
        this.size = 10;
    }
}
