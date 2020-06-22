package com.yu.service.feign;

import com.yu.util.R;
import org.springframework.stereotype.Component;

@Component
public class UserInfoFeignServiceFallback implements UserInfoFeignService {

    @Override
    public R selectUserInfoByPhone(String phone) {
        return R.error("服务异常");
    }

    @Override
    public R selectUserInfoByToken(String token) {
        return R.error("服务异常");
    }
}
