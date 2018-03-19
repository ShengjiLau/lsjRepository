package com.lcdt.clms.security.token.config;

import com.lcdt.clms.security.helper.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter{

    private String tokenHeaderKey = "j_a";

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(tokenHeaderKey);

        if (!StringUtils.isEmpty(header)) {
            Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(header);
        }


        filterChain.doFilter(request,response);

    }
}
