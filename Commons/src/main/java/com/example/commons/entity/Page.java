package com.example.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private Integer currentPage; // 当前页

    private final Integer size = 10; // 每页记录数

    private Integer pageNum; // 第几页

    private Integer count; // 总记录数

    private List<T> records; // 数据
}
