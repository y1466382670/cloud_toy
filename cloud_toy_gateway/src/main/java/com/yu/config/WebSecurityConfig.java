package com.yu.config;

//import com.yu.filter.JWTAuthenticationFilter;
import com.yu.filter.JwtAuthorizeFilter;
//import com.yu.filter.MyFilterSecurityInterceptor;
import com.yu.handler.RestAccessDeniedHandler;
import com.yu.security.UserDetailsServiceImpl;
import com.yu.security.constant.IgnoredUrlsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security 核心配置类
 * 开启控制权限至Controller
 * @author Exrickx
 * 问题是@EnableWebSecurity类没有注释。
 *
 * 不加@EnableWebSecurity注解启动会报下边错误
 * A component required a bean of type 'org.springframework.security.config.annotation.ObjectPostProcessor' that could not be found.
 * 此注解由Spring-boot自动添加，但是由于使用gateway 选择不使用Spring-boot，因此需要手动处理。
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Autowired
//    private AuthenticationSuccessHandler successHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler failHandler;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Bean
    public JwtAuthorizeFilter authorizeFilter() throws Exception{
        return new JwtAuthorizeFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
//                .authorizeRequests();
//        //除配置文件忽略路径其它所有请求都需经过认证和授权
//        for(String url:ignoredUrlsProperties.getUrls()){
//            registry.antMatchers(url).permitAll();
//        }
        http.httpBasic()
                .and()
                .csrf().disable()
                //异常处理：关于token错误，统一处理返回
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                //session管理：禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //认证请求
                .authorizeRequests()
                //路径匹配上后拦截getOfferLits器放行：这些接口上不要加权限注解，否则配置失效，变成必须传token
                .antMatchers("/login/**").permitAll()
                //除上面URI以外的任何请求都要认证
                .anyRequest()
                .authenticated()
                .and()
                .rememberMe();
        //.hasAnyAuthority("API","ROLE_USER");
        //请求前拦截器处理
        http.addFilterBefore(authorizeFilter(), UsernamePasswordAuthenticationFilter.class);
//        registry.antMatchers(HttpMethod.OPTIONS).permitAll()
////                //表单登录方式
////                .formLogin()
////                .loginPage("/login/needLogin")
////                //登录需要经过的url请求
////                .loginProcessingUrl("/api/v1/auth/login")
////                .usernameParameter("username")
////                .passwordParameter("password")
////                .permitAll()
////                //成功处理类
////                .successHandler(successHandler)
////                //失败
////                .failureHandler(failHandler)
////                .and()
////                .logout()
////                .permitAll()
//
//                .and()
//                .authorizeRequests()
//                //任何请求
//                .anyRequest()
//                //需要身份认证
//                .authenticated()
//                .and()
//                //关闭跨站请求防护
//                .csrf().disable()
//                //前后端分离采用JWT 不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //自定义权限拒绝处理类
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
//                .and()
//                .rememberMe()
//                .and()
////                //添加自定义权限过滤器
////                .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class)
////                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
//                //添加JWT过滤器 除/login其它请求都需经过此过滤器
//                .addFilterBefore(authorizeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
