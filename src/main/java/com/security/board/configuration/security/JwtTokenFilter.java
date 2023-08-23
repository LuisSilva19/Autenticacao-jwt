package com.security.board.configuration.security;

import com.security.board.exception.ResourceExceptionHandler;
import com.security.board.service.LoginService;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private LoginService loginService;
    private  HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String email = "";
        try {
            var token = jwtService.resolveToken(request);

            if (Objects.nonNull(token)) {
                email = jwtService.extractEmailFromToken(token);
            }

            if (token != null && jwtService.validateToken(token, email)) {
                var user = jwtService.getAuthentication(token);
                if (user != null) {
                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}