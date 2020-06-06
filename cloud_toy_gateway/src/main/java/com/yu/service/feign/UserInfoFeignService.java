package com.yu.service.feign;

import com.yu.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserInfoFeignService {

    /**
     * 根据用户手机号查询用户信息
     * @param phone
     * @return
     */
    @RequestMapping(value = "/user.info.selectInfoByPhone", method = RequestMethod.POST)
    R selectUserInfoByPhone(@RequestParam String phone);

    /**
     * 根据用户token查询用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/user.info.selectInfoByToken", method = RequestMethod.POST)
    R selectUserInfoByToken(@RequestParam String token);

}
