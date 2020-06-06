package com.yu.controller;

import com.yu.entity.UserInfo;
import com.yu.service.UserInfoService;
import com.yu.util.JWTUtil;
import com.yu.util.R;
import com.yu.util.RedisUtil;
import com.yu.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    RedisUtil redisTemplate;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录认证
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/auth.login", method = RequestMethod.POST)
    public R login(@RequestParam String username, @RequestParam String password) {
        if(StringUtils.isEmpty(username)){
            return R.error("请输入账号");
        }
        UserInfo userInfo = this.userInfoService.selectByPhone(username);
        if(StringUtils.isEmpty(userInfo)){
            return R.error("此账号未注册");
        }
        if(StringUtils.isEmpty(password)){
            return R.error("请输入密码");
        }
        if(!userInfo.getPassword().equals(password)){
            return R.error("密码输入错误");
        }
        //生成token
        String token = JWTUtil.createJWT(username);
        //生成refreshToken
        String refreshToken = "userToken-"+username;
        //数据放入redis
        redisTemplate.hset(refreshToken, "token", token);
        redisTemplate.hset(refreshToken, "username", username);
        //设置refreshToken的过期时间 30 天
        redisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME);
        boolean flag = userInfoService.updateUserToken(userInfo.getId(),token);
        if(!flag){
            return R.error("登陆失败，请重新登录");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("refreshToken",refreshToken);
        return R.ok().put("result", map);
    }

    /**
     * 刷新token
     */
    @RequestMapping(value = "/auth.refreshToken", method = RequestMethod.POST)
    public R refreshToken(@RequestParam String refreshToken) {
        String username = (String)redisTemplate.hget(refreshToken, "username");
        if(StringUtils.isEmpty(username)){
            return R.error("refreshToken error");
        }
        //生成新的token
        String newToken = JWTUtil.createJWT(username);
        redisTemplate.hset(refreshToken, "token", newToken);
        Map<String,Object> map = new HashMap<>();
        map.put("token",newToken);
        map.put("refreshToken",refreshToken);
        return R.ok().put("result", map);
    }

    /**
     * 校验token
     */
    @RequestMapping(value = "/auth.token.verify", method = RequestMethod.POST)
    public R verifyToken(@RequestParam String token) {
        Boolean claims = JWTUtil.isExpired(token);
        return R.ok().put("result", claims);
    }

}
