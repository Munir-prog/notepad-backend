package com.mprog.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mprog.config.jwt.JwtUtils;
import com.mprog.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final UserDetailsServiceImpl userDetailsService;

	private static final String TOKEN_PREFIX = "Bearer ";

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: ", e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)) {
			return headerAuth.substring(7);
		}

		return null;
	}
}
