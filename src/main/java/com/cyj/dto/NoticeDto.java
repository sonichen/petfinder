package com.cyj.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2022-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Notice对象", description="")
public class NoticeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "发布人")
    private String  username;

    @ApiModelProperty(value = "分类")
    private String categoriesName;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "点击率")
    private Long version;
    @ApiModelProperty(value = "收藏量")
    private Long star;
    @ApiModelProperty(value = "我的收藏情况")
    private Long myLike;

    public NoticeDto(Long id, String username, String categoriesName, String title, String content, Date createTime, Long version, Long star, Long myLike) {
        this.id = id;
        this.username = username;
        this.categoriesName = categoriesName;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.version = version;
        this.star = star;
        this.myLike = myLike;
    }
}
