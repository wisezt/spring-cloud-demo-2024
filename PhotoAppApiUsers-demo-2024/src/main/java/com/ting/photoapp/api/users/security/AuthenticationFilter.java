package com.ting.photoapp.api.users.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import com.ting.photoapp.api.users.serivce.UserService;
import com.ting.photoapp.api.users.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ting.photoapp.api.users.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;


    private Environment environment;


//    @Autowired
    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String userName = ((User)auth.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(userName);
        String tokenSecret = environment.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());

        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .subject(userName)
                .expiration(Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        System.out.println("userId: " + userName);
        System.out.println("token: " + token);


        res.addHeader("userId", userDetails.getUserId());
        res.addHeader("token", token);


    }
}
