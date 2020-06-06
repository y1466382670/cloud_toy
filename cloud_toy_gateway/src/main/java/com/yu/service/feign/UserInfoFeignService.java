package com.yu.service.feign;

import com.yu.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserInfoFeignService {

    @RequestMapping(value = "/user.info.selectInfoByPhone", method = RequestMethod.POST)
    R selectUserInfoByPhone(@RequestParam String phone);

}
