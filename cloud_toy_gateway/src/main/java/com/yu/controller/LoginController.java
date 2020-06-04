package com.yu.controller;

import com.yu.entity.UserInfo;
import com.yu.exception.CommonException;
import com.yu.security.constant.ResultCode;
import com.yu.service.UserInfoService;
import com.yu.util.JWTUtil;
import com.yu.util.R;
import com.yu.util.UuidUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrickx
 */

@RestController
public class LoginController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录认证
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestParam String username, @RequestParam String password) {
        if(org.springframework.util.StringUtils.isEmpty(username)){
            return R.error("请输入账号");
        }
        UserInfo userInfo = this.userInfoService.selectByPhone(username);
        if(org.springframework.util.StringUtils.isEmpty(userInfo)){
            return R.error("此账号未注册");
        }
        if(org.springframework.util.StringUtils.isEmpty(password)){
            return R.error("请输入密码");
        }
        if(!userInfo.getPassword().equals(password)){
            return R.error("密码输入错误");
        }
        //生成token
        String token = JWTUtil.createJWT(username);
        //生成refreshToken
        String refreshToken = UuidUtil.getUUID();
        //数据放入redis
        stringRedisTemplate.opsForHash().put(refreshToken, "token", token);
        stringRedisTemplate.opsForHash().put(refreshToken, "username", username);

        //设置refreshToken的过期时间 30 天
        stringRedisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("refreshToken",refreshToken);
        return R.ok().put("result", map);
    }

    @GetMapping(value = "/needLogin")
    public String needLogin() throws CommonException {
        throw new CommonException(ResultCode.UNAUTHORIZED, "请登陆后操作");
        //return ResUtil.getJsonStr(ResultCode.UNAUTHORIZED, "请登陆后操作");
    }

    /**
     * @Author liuheming
     * @Description 用户退出，删除redis中的token，让派发的token失效
     * @Date 16:03 2019/6/26
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/exit/{userName}")
    public String exit(@PathVariable("userName")String userName) throws CommonException {
        String token=stringRedisTemplate.opsForValue().get("token_"+userName);

        if(StringUtils.isBlank(token)){
            throw new CommonException(ResultCode.UNAUTHORIZED, "请勿重复退出");
        }
        stringRedisTemplate.delete("token_"+userName);

        return "注销成功";
    }
}
