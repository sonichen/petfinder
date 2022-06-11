package com.cyj.dto.community.post;

import lombok.Data;

@Data
public class PostQueryDto {

    private String categoriesId;
    private String keyword;

    private Long page;
    private Long pageSize;
}
