package com.yu.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String headUrl;

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "省")
    private String addressProvince;

    @ApiModelProperty(value = "市")
    private String addressCity;

    @ApiModelProperty(value = "区")
    private String addressArea;

    @ApiModelProperty(value = "openId")
    private String openId;

}