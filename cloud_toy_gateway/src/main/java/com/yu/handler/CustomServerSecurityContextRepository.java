package com.yu.handler;

import com.yu.security.UserDetailsServiceImpl;
import com.yu.util.JWTUtil;
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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
                userIdFromToken = jwtUtil.getUsernameFromToken(token);
                if (Strings.isNotBlank(userIdFromToken)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userIdFromToken);
                    authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
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