package com.ting.PhotoApp.Api.Gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderFilter
    extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

  public AuthorizationHeaderFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request =
          exchange
              .getRequest()
              .mutate()
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + config.getToken())
              .build();

      return chain.filter(exchange.mutate().request(request).build());
    };
  }

  public static class Config {
    // Add configuration properties if needed
    private String token;

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }
  }
}
