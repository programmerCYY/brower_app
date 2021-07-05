package com.example.backend.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author 
 */
@ApiModel(value="com.example.backend.pojo.User")
@Data
public class User implements Serializable {
    /**
     * 专题主键id
     */
    @ApiModelProperty(value="专题主键id")
    private String userId;

    /**
     * 别名，昵称
     */
    @ApiModelProperty(value="别名，昵称")
    private String nickname;

    /**
     * 登录名
     */
    @ApiModelProperty(value="登录名")
    private String username;

    /**
     * 微信头像地址
     */
    @ApiModelProperty(value="微信头像地址")
    private String avatar;

    /**
     * 登录密码
     */
    @ApiModelProperty(value="登录密码")
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;


    private static final long serialVersionUID = 1L;


}