package com.yu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "图书信息表")
@Data
public class BookInfo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "图书大分类编码")
    private String bookClassifyCode;

    @ApiModelProperty(value = "图书详细分类编码")
    private String bookClassifyDetailCode;

    @ApiModelProperty(value = "图书名称")
    private String bookName;

    @ApiModelProperty(value = "图书作者")
    private String bookAuthor;

    @ApiModelProperty(value = "简介")
    private String bookNote;

    @ApiModelProperty(value = "价格")
    private BigDecimal bookPrice;

    @ApiModelProperty(value = "出版社")
    private String bookPublishing;

    @ApiModelProperty(value = "条形码")
    private String bookBarCode;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态（1有效 2无效）")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "修改时间")
    private String updateDate;

}