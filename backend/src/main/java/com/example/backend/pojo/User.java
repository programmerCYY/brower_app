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

    /**
     * 微信用户唯一编号
     */
    @ApiModelProperty(value="微信用户唯一编号")
    private String unionid;

    /**
     * 用户角色
     */
    @ApiModelProperty(value="用户角色")
    private String role;

    private Date lastAccessAt;

    private Boolean isMute;

    private Boolean isValid;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getUnionid() == null ? other.getUnionid() == null : this.getUnionid().equals(other.getUnionid()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getLastAccessAt() == null ? other.getLastAccessAt() == null : this.getLastAccessAt().equals(other.getLastAccessAt()))
            && (this.getIsMute() == null ? other.getIsMute() == null : this.getIsMute().equals(other.getIsMute()))
            && (this.getIsValid() == null ? other.getIsValid() == null : this.getIsValid().equals(other.getIsValid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getUnionid() == null) ? 0 : getUnionid().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getLastAccessAt() == null) ? 0 : getLastAccessAt().hashCode());
        result = prime * result + ((getIsMute() == null) ? 0 : getIsMute().hashCode());
        result = prime * result + ((getIsValid() == null) ? 0 : getIsValid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", nickname=").append(nickname);
        sb.append(", username=").append(username);
        sb.append(", avatar=").append(avatar);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", unionid=").append(unionid);
        sb.append(", role=").append(role);
        sb.append(", lastAccessAt=").append(lastAccessAt);
        sb.append(", isMute=").append(isMute);
        sb.append(", isValid=").append(isValid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}