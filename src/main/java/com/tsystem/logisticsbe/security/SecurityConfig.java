package com.tsystem.logisticsbe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/drivers/**").hasAnyAuthority(Permission.PERMISSION_READ.getPermission(), Permission.PERMISSION_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.PERMISSION_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.PERMISSION_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.PERMISSION_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("manager")
                        .password(passwordEncoder().encode("manager"))
                        .authorities(UserRoles.MANAGER.getAuthority())
                        .build(),

                User.builder()
                        .username("driver")
                        .password(passwordEncoder().encode("driver"))
                        .authorities(UserRoles.DRIVER.getAuthority())
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
