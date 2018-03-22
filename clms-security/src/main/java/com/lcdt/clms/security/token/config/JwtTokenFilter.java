package com.lcdt.clms.security.token.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter{

    private String tokenHeaderKey = "clmstoken";

    private Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Reference
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(tokenHeaderKey);

        if (!StringUtils.isEmpty(header)) {
            Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(header);
            String userName = (String) claimsFromToken.get("userName");
            logger.info("request token username :{} ",userName);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    User user = userService.queryByPhone(userName);
                    if (jwtTokenUtil.validateToken(header, user)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user, null, null);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                request));
                        logger.info("authenticated user " + userName + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (UserNotExistException e) {
                    e.printStackTrace();
                }

            }
        }

        filterChain.doFilter(request,response);
    }

    static class TokenAuthencation{

    }

}
