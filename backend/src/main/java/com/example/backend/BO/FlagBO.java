package com.example.backend.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: rain
 * @date: 2021/6/21 21:02
 * @description:
 */
@Data
public class FlagBO {
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
}
