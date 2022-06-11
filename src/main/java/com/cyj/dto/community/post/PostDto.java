package com.cyj.dto.community.post;

import com.alibaba.druid.filter.AutoLoad;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "点赞总数")
    @TableField("likeCount")
    private Long likeCount;

    @ApiModelProperty(value = "观看量")
    @TableField("hitCount")
    private Long hitCount;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Long commentCount;
    private Long myStar;
}
