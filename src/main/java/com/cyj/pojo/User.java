package com.cyj.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

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
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "签名")
    private String introduce;

    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "身份证")
    private String idcard;
    @ApiModelProperty(value = "所在地")
    private String location;
    @ApiModelProperty(value = "角色")
    private Long role;
    @ApiModelProperty(value = "乐观锁")
    @TableLogic
    private Long deleted;


    public User(String tel,String password) {
        this.password = password;
        this.tel = tel;
    }

    public User(String username, String password, String tel) {
        this.username = username;
        this.password = password;
        this.tel = tel;
    }
}
