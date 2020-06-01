package com.yu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePage {

    @ApiModelProperty(value = "页数")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

}
