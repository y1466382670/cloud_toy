package com.yu.config;

import com.yu.filter.JwtAuthorizeFilter;
import com.yu.handler.CustomHttpBasicServerAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity  //在webflux环境下必须使用注解@EnableWebFluxSecurity才能生效
public class SecurityConfig {

    @Autowired
    private CustomHttpBasicServerAuthenticationEntryPoint customHttpBasicServerAuthenticationEntryPoint;
    @Bean
    public JwtAuthorizeFilter authorizeFilter() throws Exception{
        return new JwtAuthorizeFilter();
    }

    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {
            "/auth/**",
            "/login"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http
                .authorizeExchange()
                //无需进行权限过滤的请求路径
                .pathMatchers(excludedAuthPages).permitAll()
                //option 请求默认放行
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                //启动页面表单登陆,spring security 内置了一个登陆页面/login
//                .formLogin()
//                //认证成功
//                .authenticationSuccessHandler(loginSuccessHandler)
//                //登陆验证失败
//                .authenticationFailureHandler(loginFailureHandler)
//                //基于http的接口请求鉴权失败
//                .and()
                .exceptionHandling().authenticationEntryPoint(customHttpBasicServerAuthenticationEntryPoint)
                //必须支持跨域
                .and().csrf().disable()
                .logout().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
