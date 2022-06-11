package com.cyj.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class AdoptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String  username;
    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "宠物名字")
    private String name;

    @ApiModelProperty(value = "宠物型号 1-已免疫；2-已驱虫；3-已绝育")
    private String size;

    @ApiModelProperty(value = "宠物年龄")
    private String age;

    @ApiModelProperty(value = "宠物性别")
    private String sex;

    @ApiModelProperty(value = "宠物状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @ApiModelProperty(value = "主人描述")
    private String description;

    @ApiModelProperty(value = "领养条件")
    private String requirement;



    private String address;
    private String nickname;
    private String tel;

    private List<String> images;
    private Long star;


}
