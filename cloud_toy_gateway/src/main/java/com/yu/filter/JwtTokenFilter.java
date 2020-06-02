package com.yu.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.util.JWTUtil;
import com.yu.util.R;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 描述: JwtToken 过滤器
 * @Auther: yanzhiyu
 * @Date: 2020/06/02 15:00
 */
@Component
public class JwtTokenFilter implements GlobalFilter, Ordered{

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Value("${jwt.ignoreUrlList}")
    private String ignoreUrl;

    private ObjectMapper objectMapper;

    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        //跳过不需要验证的路径
        if(!StringUtils.isEmpty(ignoreUrl)){
//            List<String> ignoreUrlList = Arrays.asList(ignoreUrl.split(","));
            System.out.println(ignoreUrl);
            System.out.println(url);
            if(ignoreUrl.contains(url)){
                return chain.filter(exchange);
            }
        }
        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpResponse resp = exchange.getResponse();
        if(StringUtils.isBlank(token)){
            //没有token
            return authErro(resp,"鉴权失败，无token或类型");
        }else{
            //有token
            try {
                boolean flag = JWTUtil.isExpired(token);
                if(flag){
                    return authErro(resp,"登录信息已过期");
                }
                return chain.filter(exchange);
            }catch (ExpiredJwtException e){
                logger.error(e.getMessage(),e);
                if(e.getMessage().contains("Allowed clock skew")){
                    return authErro(resp,"认证过期");
                }else{
                    return authErro(resp,"认证失败");
                }
            }catch (Exception e) {
                logger.error(e.getMessage(),e);
                return authErro(resp,"认证失败");
            }
        }
    }

    /**
     * 认证错误输出
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp,String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(R.error().put("code",HttpStatus.UNAUTHORIZED).put("msg",mess));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
