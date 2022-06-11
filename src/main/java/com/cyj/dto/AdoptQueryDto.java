package com.cyj.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cyj
 * @since 2022-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Adopt对象", description="")
public class AdoptQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userId;

    private Long categoriesId;
    @ApiModelProperty(value = "宠物型号 1-已免疫；2-已驱虫；3-已绝育")
    private String size;

    private String name;
    @ApiModelProperty(value = "宠物年龄")
    private String age;

    @ApiModelProperty(value = "宠物性别")
    private String sex;

    @ApiModelProperty(value = "宠物状态")
    private String status;

    private String address;

    private String deleted;
    private String keyword;
    private int page;
    private int pagesize;


}
