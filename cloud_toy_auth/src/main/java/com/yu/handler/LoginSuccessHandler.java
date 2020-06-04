package com.yu.handler;

import com.yu.security.AuthUserDetails;
import com.yu.util.JWTUtil;
import com.yu.util.R;
import com.yu.util.ResponseUtil;
import com.yu.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登陆认证成功处理过滤器
 * @author yanzhiyu
 * @Date 2020/6/4 14:27
 **/
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    StringRedisTemplate redisTemplate;
    /**
     * @Author yanzhiyu
     * @Description 用户认证成功后 生成token并返回
     * @Date 2020/6/4 14:27
     * @Param [request, response, authentication]
     * @return void
     **/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        AuthUserDetails authUserDetails=(AuthUserDetails)authentication.getPrincipal();//从内存中获取当前认证用户信息

        //创建token
        String accessToken = JWTUtil.createJWT(authUserDetails.getUsername());
        //生成refreshToken
        String refreshToken = UuidUtil.getUUID();
        //数据放入redis
        redisTemplate.opsForHash().put(refreshToken, "token", accessToken);
        redisTemplate.opsForHash().put(refreshToken, "username", authUserDetails.getUsername());
        //设置refreshToken的过期时间 30 天
        redisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        HashMap<String,String> map=new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);
        ResponseUtil.out(response, R.ok().put("result",map).toString());
    }

}


