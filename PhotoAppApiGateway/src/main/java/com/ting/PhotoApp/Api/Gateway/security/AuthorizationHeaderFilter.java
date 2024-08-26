package com.ting.PhotoApp.Api.Gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.bouncycastle.util.encoders.Base64;
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
import io.jsonwebtoken.Jwts;
//import org.hibernate.cfg.Environment;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.SignalType;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    public Environment env;

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization hear", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

//            try {
//                if (!isJwtValid(jwt)) {
//                    onError(exchange, "JWT token is NOT valid!", HttpStatus.UNAUTHORIZED);
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String noAuthorizationHear, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

//    private boolean isJwtValid(String jwt) throws Exception {
//
//
////        try {
////            Claims claims = Jwts.parser()
////                    .setSigningKey(env.getProperty("token.secret"))
////                    .parseClaimsJws(jwt)
////                    .getBody();
////
////            System.out.println("JWT is valid. Claims: " + claims);
////        } catch (SignatureException e) {
////            System.out.println("Invalid JWT signature.");
////        } catch (Exception e) {
////            System.out.println("Invalid JWT token.");
////        }
//
//
//        boolean isValid = true;
//        String tokenSecret = env.getProperty("token.secret");
//        byte[] secrectKeyBytes = Base64.encode(tokenSecret.getBytes());
//        SecretKey secretKey = new SecretKeySpec(secrectKeyBytes, SignatureAlgorithm.HS512.getJcaName());
//        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
//
//        try {
//            jwtParser.parse(jwt);
//        } catch (Exception e) {
//            throw new Exception("Could not verify JWT token integrity!", e);
//        }
//
//        return true;
//
//    }

}