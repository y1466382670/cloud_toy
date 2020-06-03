package com.yu.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "BookInfoController", description = "图书信息")
@RestController
public class BookInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BookClassifyDetailController.class);

}
