package org.j2os.filter;

import lombok.extern.slf4j.Slf4j;
import org.j2os.common.TokenProvider;
import org.j2os.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Bahador, Amirsam
 */
@Controller
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public SecurityFilter(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            log.info("doFilterInternal");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = tokenProvider.verifyToken(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities()));
        } catch (Exception e) {
            log.warn("request with token error: {}", e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
