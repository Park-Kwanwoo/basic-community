package com.gwan.blog.request.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public class BoardPagination {

    private static final int MAX_SIZE = 2000;

    private int page;

    private int size;

    public long getOffset() {
        return (long) (max(1, this.page) - 1) * min(size, MAX_SIZE);
    }

    public BoardPagination() {
        this.page = 1;
        this.size = 10;
    }

     public Pageable getPageable()  {
         return PageRequest.of(this.page - 1, this.size);
     }
}
