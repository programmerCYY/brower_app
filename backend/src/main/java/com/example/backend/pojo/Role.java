package com.example.backend.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * flag
 * @author 
 */
@ApiModel(value="com.example.backend.pojo.Role")
@Data
public class Role implements Serializable {
    /**
     * 书签url
     */
    @ApiModelProperty(value="书签url")
    private String flagUrl;

    /**
     * 书签网页图片
     */
    @ApiModelProperty(value="书签网页图片")
    private String flagIcon;

    /**
     * 书签网页名称
     */
    @ApiModelProperty(value="书签网页名称")
    private String flagName;

    /**
     * 书签用户名
     */
    @ApiModelProperty(value="书签用户名")
    private String flagUser;

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
        Role other = (Role) that;
        return (this.getFlagUrl() == null ? other.getFlagUrl() == null : this.getFlagUrl().equals(other.getFlagUrl()))
            && (this.getFlagIcon() == null ? other.getFlagIcon() == null : this.getFlagIcon().equals(other.getFlagIcon()))
            && (this.getFlagName() == null ? other.getFlagName() == null : this.getFlagName().equals(other.getFlagName()))
            && (this.getFlagUser() == null ? other.getFlagUser() == null : this.getFlagUser().equals(other.getFlagUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlagUrl() == null) ? 0 : getFlagUrl().hashCode());
        result = prime * result + ((getFlagIcon() == null) ? 0 : getFlagIcon().hashCode());
        result = prime * result + ((getFlagName() == null) ? 0 : getFlagName().hashCode());
        result = prime * result + ((getFlagUser() == null) ? 0 : getFlagUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flagUrl=").append(flagUrl);
        sb.append(", flagIcon=").append(flagIcon);
        sb.append(", flagName=").append(flagName);
        sb.append(", flagUser=").append(flagUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}