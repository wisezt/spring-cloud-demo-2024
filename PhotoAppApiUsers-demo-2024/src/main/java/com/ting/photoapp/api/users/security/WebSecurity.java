package com.ting.photoapp.api.users.security;

import com.ting.photoapp.api.users.serivce.UserService;
import jakarta.servlet.*;
import org.bouncycastle.pqc.jcajce.provider.bike.BCBIKEPrivateKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.logging.LogRecord;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Environment environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, userService, environment);
        authenticationFilter.setFilterProcessesUrl("/test/login");


        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
//                .addFilterBefore(ipAddressFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilter(authenticationFilter)
                .authenticationManager(authenticationManager)
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























