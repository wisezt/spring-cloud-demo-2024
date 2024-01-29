package com.ting.photoapp.api.users.security;

import jakarta.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.logging.LogRecord;

@Configuration
@EnableWebSecurity
public class WebSecurity {

//    @Bean
//    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
//
//        http.authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/users").permitAll().requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.headers().frameOptions().disable();
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .addFilterBefore(ipAddressFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.disable())
                )
        ;

        return http.build();
    }

    @Bean
    public Filter ipAddressFilter() {
        IpAddressFilter ipAddressFilter = new IpAddressFilter();
        ipAddressFilter.setAllowedIpAddresses("192.168.52.128", "127.0.0.1"); // Add your allowed IP addresses
        return ipAddressFilter;
    }


}























