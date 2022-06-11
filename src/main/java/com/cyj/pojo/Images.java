package com.cyj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@ApiModel(value="Images对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "领养的宠物id")
    @TableField("workId")
    private Long workId;

    @ApiModelProperty(value = "图片路径")
    private String path;

    @ApiModelProperty(value = "模块")
    private String module;

    public Images(Long workId, String path, String module) {
        this.workId = workId;
        this.path = path;
        this.module = module;
    }
}
