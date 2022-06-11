package com.cyj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Adopt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "分类id")
    @TableField("categoriesId")
    private Long categoriesId;

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

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "主人描述")
    private String description;

    @ApiModelProperty(value = "领养条件")
    private String requirement;

    @ApiModelProperty(value = "是否发布：0-发布，1-已经完成")
    @TableLogic
    private Long deleted;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "联系人昵称")
    private String nickname;
    @ApiModelProperty(value = "联系电话")
    private String tel;
    private String mark;
    private String images;


}
