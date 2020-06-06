package com.yu.security;

import com.yu.dao.UserInfoMapper;
import com.yu.entity.UserInfo;
import com.yu.service.feign.UserInfoFeignService;
import com.yu.util.R;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Description 修改为数据库读取的模式, 用于根据登录时输入的账号来获取用户并得到权限
 *   实现security提供的 用户信息获取接口  并按照业务增加redis 登陆限制
 * @author  yanzhiyu
 * @Date 2020/06/04
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService /**ReactiveUserDetailsService*/ {
    //登入重试时间
    @Value("${security.loginAfterTime}")
    private Integer loginAfterTime;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoFeignService userInfoFeignService;

//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        String flagKey = "loginFailFlag:"+username;
//        String value = redisTemplate.opsForValue().get(flagKey);
//        if(StringUtils.isNotBlank(value)){
//            //超过限制次数
//            throw new UsernameNotFoundException("登录错误次数超过限制，请"+loginAfterTime+"分钟后再试");
//        }
//        //查询用户信息
//        UserInfo userInfo = userInfoMapper.selectByPhone(username);
//        if(null==userInfo){
//            throw new UsernameNotFoundException("the user does not exist");
//        }
//        UserDetails user = User.withUsername(userInfo.getPhone())
//                .build();
//        return Mono.just(user);
//    }

    /**
     * 通过用户主键获取账号信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @description spring security底层调用此方法
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String flagKey = "loginFailFlag:"+username;
        String value = redisTemplate.opsForValue().get(flagKey);
        if(StringUtils.isNotBlank(value)){
            //超过限制次数
            throw new UsernameNotFoundException("登录错误次数超过限制，请"+loginAfterTime+"分钟后再试");
        }
        R o = userInfoFeignService.selectUserInfoByPhone(username);
        //查询用户信息
        UserInfo userInfo = userInfoMapper.selectByPhone(username);
        if(null==userInfo){
            throw new UsernameNotFoundException("the user does not exist");
        }
        return new AuthUserDetails(userInfo);
    }

}

