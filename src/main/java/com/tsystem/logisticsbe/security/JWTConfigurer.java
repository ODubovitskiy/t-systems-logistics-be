package com.tsystem.logisticsbe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JWTTokenFilter jwtTokenFilter;
    private final CorsFilter corsFilter;

    @Autowired
    public JWTConfigurer(JWTTokenFilter jwtTokenFilter, CorsFilter corsFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.corsFilter = corsFilter;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
