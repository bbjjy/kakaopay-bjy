package com.kakaopay.api.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
/**
 * jwt 필터
 * @author bbjjy
 *
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // Jwt Provier 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //token 재발행
        if (token != null && token.startsWith("Bearer") && jwtTokenProvider.validateToken(token.replace("Bearer ", ""))) {
        	Authentication auth = jwtTokenProvider.getAuthentication(token.replace("Bearer ", ""));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        if (token != null && jwtTokenProvider.validateToken(token)) {
        	Authentication auth = jwtTokenProvider.getAuthentication(token);
        	SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
