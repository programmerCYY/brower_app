package com.example.backend.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * history
 * @author 
 */
@ApiModel(value="com.example.backend.pojo.History")
@Data
public class History implements Serializable {
    /**
     * 自增组件
     */
    @ApiModelProperty(value="自增组件")
    private Integer id;

    /**
     * 历史记录url
     */
    @ApiModelProperty(value="历史记录url")
    private String historyUrl;

    /**
     * 历史记录网页名称
     */
    @ApiModelProperty(value="历史记录网页名称")
    private String historyName;

    /**
     * 历史记录网页图片
     */
    @ApiModelProperty(value="历史记录网页图片")
    private String historyIcon;

    /**
     * 历史记录时间
     */
    @ApiModelProperty(value="历史记录时间")
    private Date historyTime;

    /**
     * 历史记录用户名
     */
    @ApiModelProperty(value="历史记录用户名")
    private String historyUser;

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
        History other = (History) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHistoryUrl() == null ? other.getHistoryUrl() == null : this.getHistoryUrl().equals(other.getHistoryUrl()))
            && (this.getHistoryName() == null ? other.getHistoryName() == null : this.getHistoryName().equals(other.getHistoryName()))
            && (this.getHistoryIcon() == null ? other.getHistoryIcon() == null : this.getHistoryIcon().equals(other.getHistoryIcon()))
            && (this.getHistoryTime() == null ? other.getHistoryTime() == null : this.getHistoryTime().equals(other.getHistoryTime()))
            && (this.getHistoryUser() == null ? other.getHistoryUser() == null : this.getHistoryUser().equals(other.getHistoryUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHistoryUrl() == null) ? 0 : getHistoryUrl().hashCode());
        result = prime * result + ((getHistoryName() == null) ? 0 : getHistoryName().hashCode());
        result = prime * result + ((getHistoryIcon() == null) ? 0 : getHistoryIcon().hashCode());
        result = prime * result + ((getHistoryTime() == null) ? 0 : getHistoryTime().hashCode());
        result = prime * result + ((getHistoryUser() == null) ? 0 : getHistoryUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", historyUrl=").append(historyUrl);
        sb.append(", historyName=").append(historyName);
        sb.append(", historyIcon=").append(historyIcon);
        sb.append(", historyTime=").append(historyTime);
        sb.append(", historyUser=").append(historyUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}