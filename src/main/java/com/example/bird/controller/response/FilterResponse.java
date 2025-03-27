package com.example.bird.controller.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilterResponse<T> {
    private int count;
    private int page;
    private int pageSize;
    private List<T> resultList;

    public FilterResponse(int page, int pageSize) {
        this.count = 0;
        this.page = page;
        this.pageSize = pageSize;
        this.resultList = new ArrayList<>();
    }

    public FilterResponse(int count, int page, int pageSize, List<T> resultList) {
        this.count = count;
        this.page = page;
        this.pageSize = pageSize;
        this.resultList = resultList;
    }
}
