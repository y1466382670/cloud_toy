package com.yu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "图书库存表")
public class BookStock {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "图书id")
    private Integer bookId;

    @ApiModelProperty(value = "图书名称")
    private String bookName;

    @ApiModelProperty(value = "库存数量")
    private Integer stockNum;

}