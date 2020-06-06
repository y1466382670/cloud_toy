package com.yu.filter;

import com.alibaba.fastjson.JSON;
import com.yu.entity.UserInfo;
import com.yu.security.constant.SecurityConstant;
import com.yu.service.UserInfoService;
import com.yu.util.JWTUtil;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问请求拦截器
 * @author liudongxin
 * @date   2019/3/27
 */
@Service
public class JwtAuthorizeFilter implements GlobalFilter, Ordered {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserInfoService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizeFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("开始了吗？");
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                    return super.writeWith(fluxBody.map(dataBuffer -> {
//                        // probably should reuse buffers
//                        byte[] content = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(content);
//                        HttpResponseWrapper httpResponseWrapper = JSON.parseObject(content, HttpResponseWrapper.class);
//                        if(httpResponseWrapper != null && httpResponseWrapper.isSuccess()){
//                            try {
//                                LoginRepDto loginRepDto = JSON.parseObject(JSON.toJSONString(httpResponseWrapper.getResult()),LoginRepDto.class);
//                                if(Strings.isNotBlank(loginRepDto.getId())){
//                                    String token = jwtTokenManager.generateToken(loginRepDto.getId());
//                                    Boolean registered = loginRepDto.getRegistered();
//                                    JSONObject jsonObject = new JSONObject();
//                                    jsonObject.put("token",token);
//                                    jsonObject.put("registered",registered);
//                                    HttpResponseWrapper success = HttpResponseWrapper.success(jsonObject);
//                                    //释放掉内存
//                                    DataBufferUtils.release(dataBuffer);
//                                    byte[] uppedContent = JSON.toJSONBytes(success);
//                                    return bufferFactory.wrap(uppedContent);
//                                }
//                            } catch (Exception e) {
//                                logger.error("处理认证请求的响应出错",e);
//                            }
//                        }
//                        return bufferFactory.wrap(content);
//                    }));
//                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        logger.info("验证Token拦截器开始");
//        logger.info("请求OPTIONS为：OPTIONS={}",request.getMethod());
//        if("OPTIONS".equals(request.getMethod())){
//            logger.info("OPTIONS请求以放过：OPTIONS={}",request.getMethod());
//            return;
//        }
//        String header = request.getHeader(SecurityConstant.HEADER);
//        if (header == null || !header.startsWith(SecurityConstant.HEADER)) {
//            //logger.info("验证Token拦截器：未登陆访问已拦截！");
//            chain.doFilter(request, response);
//            //throw new LoginException("请先登录！");
//            return;
//        }
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        //验证身份信息，设置到上下文，使用时可以直接获取用户名
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//        logger.info("验证Token拦截器结束");
//    }
//
//    /**
//     * 登录后，携带的token做身份验证
//     * @param request
//     * @return
//     */
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        String authToken = request.getHeader(SecurityConstant.HEADER);
//        logger.info("是否携带Token拦截开始,请求参数：request={}",authToken);
//        if (authToken != null) {
//            //去除Header中tokenHead
//            final String token = authToken.substring(SecurityConstant.TOKEN_SPLIT.length());
//            //校验token
//            String user = jwtUtil.getUsernameFromToken(token);
//            logger.info("是否携带Token拦截开始,token-->用户名：user={}",user);
//            if(StringUtils.isEmpty(user)){
//                throw new UsernameNotFoundException("用户不存在！");
//            }
//            //验证Token
//            if(!jwtUtil.isExpired(token)){
//                //获取账号有否有效
//                UserInfo account = userService.getUserAccountByToken(token);
//                if(account!=null) {
//                    //调用UserSecurityService.loadUserByUsername()映射到UserDetails里，再校验密码、用户名、角色、账号是否有效等
//                    return new UsernamePasswordAuthenticationToken(user, account.getPassword());
//                }
//            }else{
//                logger.info("是否携带Token拦截,user={}：Token已过期！",user);
//                throw new UsernameNotFoundException("Token已过期！");
//            }
//        }
//        return null;
//    }
}
