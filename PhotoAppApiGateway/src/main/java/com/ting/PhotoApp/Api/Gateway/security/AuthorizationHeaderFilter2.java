package com.ting.PhotoApp.Api.Gateway.security;

import io.jsonwebtoken.*;
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
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
//import javax.xml.bind.DatatypeConverter;


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

        System.out.println("GatewayFilter");

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization hear", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            System.out.println("authorizationHeader: " + authorizationHeader);
            String jwt = authorizationHeader.replace("Bear ", "");
            System.out.println("jwt: " + jwt);

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
        String BASE64_SECRET = "wHf65tb0AYFh8Mc4IN6F/Tfr4Xn9d8sW6GZmZZ8TnW3yNfFA5/Xi6Hlc0GiHb3zhL5JbZZP6VPx1IMn5GbIXfQ==";

        try {
            // Decode the Base64 secret key
            byte[] secretKeyBytes = Base64.getDecoder().decode(BASE64_SECRET.getBytes());
            SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
            // Print the byte array
            System.out.println("Signature Bytes: ");
            for (byte b : secretKeyBytes) {
                System.out.printf("%02x ", b);
            }

            // Parse the JWT
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log and handle the error
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

}