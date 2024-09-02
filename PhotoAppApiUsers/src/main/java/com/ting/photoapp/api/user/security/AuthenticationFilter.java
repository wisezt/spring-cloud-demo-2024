package com.ting.photoapp.api.user.security;

import com.ting.photoapp.api.user.service.UsersService;
import com.ting.photoapp.api.user.shared.UserDTO;
import com.ting.photoapp.api.user.ui.model.LoginRequestModel;
import io.jsonwebtoken.Jwts;
//import org.hibernate.cfg.Environment;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

//@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    @Autowired
    private UsersService usersService;

//    @Autowired
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UsersService usersService, Environment env) {
        super(authenticationManager);
        setFilterProcessesUrl("/login");
        this.usersService = usersService;
                this. env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDTO userDetails = usersService.getUserDetailsByEmail(userName);
//        String tokenSecret = "wHf65tb0AYFh8Mc4IN6F/Tfr4Xn9d8sW6GZmZZ8TnW3yNfFA5/Xi6Hlc0GiHb3zhL5JbZZP6VPx1IMn5GbIXfQ==";
//        Environment env = new StandardEnvironment();
        String tokenSecret = env.getProperty("token.secret");
        System.out.println("token.secret: " + tokenSecret);
        byte[] secretKeyBytes = Base64.getDecoder().decode(tokenSecret.getBytes());
        // Print the byte array
        System.out.println("Signature Bytes: ");
        for (byte b : secretKeyBytes) {
            System.out.printf("%02x ", b);
        }

        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .subject(userName)
                .expiration(Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration_time")))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        System.out.println("userId:" + userName);
        System.out.println("token:" + token);


        res.addHeader("userId", userDetails.getUserId());
        res.addHeader("token", token);


    }


}


