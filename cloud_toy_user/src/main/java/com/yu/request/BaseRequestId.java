package com.yu.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseRequestId {

    @ApiModelProperty(value = "主键")
    private int id;

}
