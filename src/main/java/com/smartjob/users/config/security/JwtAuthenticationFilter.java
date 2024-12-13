package com.smartjob.users.config.security;

import com.smartjob.users.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (!isValidAuthorizationHeader(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromHeader(authorizationHeader);
        if (!authService.isValidToken(token)) {
            handleInvalidToken(response);
        } else {
            authenticateUser(token);
            filterChain.doFilter(request, response);
        }

    }

    private void authenticateUser(String token) {
        try {
            String username = authService.getTokenBody(token).getSubject();
            User principal = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("Failed to authenticate user: {}", e.getMessage());
        }
    }

    private boolean isValidAuthorizationHeader(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX);
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

    public void handleInvalidToken(final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{ \"mensaje_error_entry_point\": \"" + "Token inválido" + "\" }");
    }
}
