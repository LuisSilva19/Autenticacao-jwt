package com.security.board.configuration.security;

import com.security.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final LoginService loginService;

    @Value("${cors.originPatters:default}")
    private String corsOriginPatters = "";

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtTokenFilter(jwtService, loginService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
                .authorizeRequests()
                .antMatchers("/v1/credit/login",
                                        "/v1/credit/creationHistory/by-date",
                                        "/v1/credit/creationHistory/by-range-date")
                    .hasRole("ADMIN")
                .antMatchers("/auth/login", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/health").permitAll()
                .anyRequest()
                    .authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}