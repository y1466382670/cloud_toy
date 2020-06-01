package com.yu.controller;

import com.yu.util.JWTUtil;
import com.yu.util.R;
import com.yu.util.UuidUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 登录认证
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "auth.login", method = RequestMethod.POST)
    public R login(@RequestParam String username, @RequestParam String password) {
        if("admin".equals(username) && "admin".equals(password)){
            //生成token
            String token = JWTUtil.createJWT(username);

            //生成refreshToken
            String refreshToken = UuidUtil.getUUID();

            //数据放入redis
            redisTemplate.opsForHash().put(refreshToken, "token", token);
            redisTemplate.opsForHash().put(refreshToken, "username", username);

            //设置token的过期时间
            redisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("refreshToken",refreshToken);
            return R.ok().put("result", map);
        }else{
            return R.error();
        }
    }

    /**
     * 刷新token
     */
    @RequestMapping(value = "auth.refreshToken", method = RequestMethod.POST)
    public R refreshToken(@RequestParam String refreshToken) {
        String username = (String)redisTemplate.opsForHash().get(refreshToken, "username");
        if(StringUtils.isEmpty(username)){
            return R.error("refreshToken error");
        }
        //生成新的token
        String newToken = JWTUtil.createJWT(username);
        redisTemplate.opsForHash().put(refreshToken, "token", newToken);
        Map<String,Object> map = new HashMap<>();
        map.put("token",newToken);
        map.put("refreshToken",refreshToken);
        return R.ok().put("result", map);
    }

    /**
     * 校验token
     */
    @RequestMapping(value = "auth.token.verify", method = RequestMethod.POST)
    public R verifyToken(@RequestParam String token) {
        Boolean claims = JWTUtil.isExpired(token);
        return R.ok().put("result", claims);
    }

//    @GetMapping("/")
//    public String index() {
//        return "auth-service: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//    }


}
