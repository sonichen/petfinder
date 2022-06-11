package com.cyj.dto;

import lombok.Data;

@Data
public class NoticeQueryDto {
    private String categoriesId;
    private int page=1;
    private int pageSize=5;
}
