package com.cyj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
 * @since 2022-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Post对象", description="")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "分类名称")
    @TableField("categoriesId")
    private Long categoriesId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "收藏总数")
    @TableField("starCount")
    private Long starCount;

    @ApiModelProperty(value = "点赞总数")
    @TableField("likeCount")
    private Long likeCount;

    @ApiModelProperty(value = "观看量")
    @TableField("hitCount")
    private Long hitCount;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    private Long version;

    @TableLogic
    private Long deleted;


}
