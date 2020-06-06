package com.yu.handler;

import com.alibaba.fastjson.JSONObject;
import com.yu.entity.UserInfo;
import com.yu.security.UserDetailsServiceImpl;
import com.yu.service.feign.UserInfoFeignService;
import com.yu.util.JWTUtil;
import com.yu.util.MapToJsonUtil;
import com.yu.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
/**
 * Token 校验过滤器
 * 获取 Header 中的 token 信息，并校验合法性以及可用性
 */
public class CustomServerSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserInfoFeignService userInfoFeignService;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        UsernamePasswordAuthenticationToken authentication = null;
        String userIdFromToken = null;
        try {
            if (Strings.isNotBlank(token)) {
                //使用token去表中 查找用户
                R r = this.userInfoFeignService.selectUserInfoByToken(token);
                //将返回的map转为json
                JSONObject jsonObj = MapToJsonUtil.feignMapToJson(r);
                //如果jsonObj不为空且查找到了用户
                if(!StringUtils.isEmpty(jsonObj) && !StringUtils.isEmpty(jsonObj.get("phone"))){
                    //通过token获得用户名
                    userIdFromToken = jwtUtil.getUsernameFromToken(token);
                    if (!StringUtils.isEmpty(userIdFromToken)) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userIdFromToken);
                        authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(userDetails);
                    }
                }
            }
        } catch (Exception e) {
            log.error(String.format("CustomServerSecurityContextRepository 认证异常,token{%s}", token), e);
        }
        if (authentication != null) {
            return Mono.just(new SecurityContextImpl(authentication));
        } else {
            return Mono.empty();
        }
    }
}