package com.kakaopay.api.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰 갱신 전달
 * @author bbjjy
 *
 */
@RequiredArgsConstructor
@Component
public class JwtTokenRefreshFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		// token 재발행
		if (token != null && token.startsWith("Bearer ")
				&& jwtTokenProvider.validateToken(token.replace("Bearer ", ""))) {
			String refeshtoken = jwtTokenProvider.refreshToken(token.replace("Bearer ", ""));
			response.setHeader("Authorization", refeshtoken);
		} else if (token != null) {
			response.setHeader("Authorization", request.getHeader("Authorization"));
		}
		filterChain.doFilter(request, response);
	}
}