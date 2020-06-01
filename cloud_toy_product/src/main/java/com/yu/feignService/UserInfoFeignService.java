package com.yu.feignService;

import com.yu.request.BaseRequestId;
import com.yu.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("server-user")
public interface UserInfoFeignService {

    @RequestMapping(value = "/user.info.getDetail", method = RequestMethod.POST)
    R getDetail(@RequestBody BaseRequestId requestId);

}
