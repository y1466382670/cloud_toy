//package com.yu.filter;
//
//import com.yu.exception.CommonException;
//import com.yu.security.constant.IgnoredUrlsProperties;
//import com.yu.security.constant.ResultCode;
//import com.yu.security.constant.SecurityConstant;
//import com.yu.security.pojo.MyGrantedAuthority;
//import com.yu.util.R;
//import com.yu.util.ResponseUtil;
//import com.yu.util.SpringUtil;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * JWT过滤器1
// */
//public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
//        super(authenticationManager, authenticationEntryPoint);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        IgnoredUrlsProperties ignoredUrlsProperties= SpringUtil.getBean("ignoredUrlsProperties", IgnoredUrlsProperties.class);
//        String Requesturl=request.getRequestURI();
//        PathMatcher pathMatcher = new AntPathMatcher();
//        if(null != ignoredUrlsProperties){
//            for(String url:ignoredUrlsProperties.getUrls()){
//                if(pathMatcher.match(url,Requesturl)){
//                    chain.doFilter(request, response);
//                    return;
//                }
//            }
//        }
//        //获取请求头
//        String header = request.getHeader(SecurityConstant.HEADER);
//        //如果请求头中不存在 或  格式不对  则进入下个过滤器
//        if (StringUtils.isBlank(header) || !header.startsWith(SecurityConstant.TOKEN_SPLIT)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        try {
//            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception e) {
//            ResponseUtil.out(response, R.error(e.getMessage()).toString());
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    /**
//     * @Author liuheming
//     * @Description 对token进行解析认证
//     * @Date 11:11 2019/5/7
//     * @Param [request, response]
//     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//     **/
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws CommonException {
//
//        String token = request.getHeader(SecurityConstant.HEADER);
//        if (StringUtils.isNotBlank(token)) {
//            // 解析token
//            Claims claims = null;
//            try {
//                claims = Jwts.parser()
//                        .setSigningKey(SecurityConstant.tokenSigningKey)
//                        .parseClaimsJws(token.replace(SecurityConstant.TOKEN_SPLIT, ""))
//                        .getBody();
//
//                //获取用户名
//                String username = claims.getSubject();
//
//                //获取权限
//                List<MyGrantedAuthority> authorities = new ArrayList<MyGrantedAuthority>();
//                String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
//
//                if (StringUtils.isNotBlank(authority)) {
//                    JSONArray list=JSONArray.parseArray(authority);
//                    for (int i=0;i<list.size();i++){
//                        JSONObject jsonObject=list.getJSONObject(i);
//                        authorities.add(new MyGrantedAuthority(jsonObject.getString("path"),jsonObject.getString("method")));
//                    }
//                }
//                if (StringUtils.isNotBlank(username)) {
//                    //此处password不能为null
//                    User principal = new User(username, "", authorities);
//                    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
//                }
//            } catch (ExpiredJwtException e) {
//                throw new CommonException(ResultCode.BAD_REQUEST, "登录已失效，请重新登录");
//            } catch (Exception e) {
//                throw new CommonException(ResultCode.BAD_REQUEST, "解析token错误");
//            }
//        }
//        return null;
//    }
//
//}
