package com.ting.PhotoApp.Api.Gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
//import org.hibernate.cfg.Environment;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;

import javax.crypto.SecretKey;
import java.util.Base64;


@Component
public class AuthorizationHeaderFilter2 extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter2.Config> {

    @Autowired
    public Environment env;

    public static class Config {
    }

    public AuthorizationHeaderFilter2() {
        super(AuthorizationHeaderFilter2.Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization hear", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bears", "");

            try {
                if (!isJwtValid(jwt)) {
                    onError(exchange, "JWT token is NOT valid!", HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String noAuthorizationHear, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) throws Exception {
        String tokenSecret = env.getProperty("token.secret");

        // Decode the secret key
        byte[] secretKeyBytes = Base64.getDecoder().decode(tokenSecret);
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        // Create a JwtParser with the secret key
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();

        try {
            // Parse the JWT and validate its signature
            Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
            System.out.println("JWT is valid. Claims: " + claims);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature.");
            return false;
        } catch (Exception e) {
            System.out.println("Invalid JWT token.");
            return false;
        }
    }

}