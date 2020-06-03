package com.yu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "图书详细分类表")
@Data
public class BookClassifyDetail {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "大分类编码")
    private String bookClassifyCode;

    @ApiModelProperty(value = "详细分类编码")
    private String bookClassifyDetailCode;

    @ApiModelProperty(value = "详细分类名称")
    private String bookClassifyDetailName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "修改时间")
    private String updateDate;

}