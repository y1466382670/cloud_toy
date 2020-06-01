package com.yu.controller;

import com.yu.feignService.UserInfoFeignService;
import com.yu.request.BaseRequestId;
import com.yu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

    @Autowired
    private UserInfoFeignService userInfoFeignService;

    /**
     * 查询会员用户列表
     * @param
     * @return
     */
    @RequestMapping(value = "product.details", method = RequestMethod.POST)
    public R selectAll(){
        BaseRequestId ba = new BaseRequestId();
        ba.setId(930);
        R result = userInfoFeignService.getDetail(ba);
        return R.ok().put("result",result);
    }

}
