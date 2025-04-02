package com.example.bird.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "查询数据返回结果")
@Data
public class FilterResponse<T> {
    @Schema(description = "条件过滤的数据总数")
    private int count;
    @Schema(description = "当前页码")
    private int page;
    @Schema(description = "分页大小")
    private int pageSize;
    @Schema(description = "数据")
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
