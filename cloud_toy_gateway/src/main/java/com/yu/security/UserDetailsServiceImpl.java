package com.yu.security;

import com.yu.dao.UserInfoMapper;
import com.yu.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 修改为数据库读取的模式, 用于根据登录时输入的账号来获取用户并得到权限
 *   实现security提供的 用户信息获取接口  并按照业务增加redis 登陆限制
 * @author  yanzhiyu
 * @Date 2020/06/04
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    //登入重试时间
    @Value("${security.loginAfterTime}")
    private Integer loginAfterTime;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * @author yanhiyu
     * @Description 实现用户信息查询方法 让DaoAuthenticationProvider 获取到数据库获中用户数据
     * @Param [username]
     * @return org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String flagKey = "loginFailFlag:"+username;
        String value = redisTemplate.opsForValue().get(flagKey);
        if(StringUtils.isNotBlank(value)){
            //超过限制次数
            throw new UsernameNotFoundException("登录错误次数超过限制，请"+loginAfterTime+"分钟后再试");
        }

        //查询用户信息
        UserInfo user = userInfoMapper.selectByPhone(username);
        if(null==user){
            throw new UsernameNotFoundException("the user does not exist");
        }
//        if(authUserPoJo.getRoleInfos()==null || authUserPoJo.getRoleInfos().isEmpty()){
//            throw new UsernameNotFoundException("当前用户无角色");
//        }
        return new AuthUserDetails(user);
    }
}

