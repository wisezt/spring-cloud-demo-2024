package com.ting.PhotoApp.Api.Gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GatewayController {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @GetMapping("/test")
    public String test() {
        return "test\t" + env.getProperty("token.secret");
    }

    @GetMapping("/gateway/routes")
    public Flux<RouteDefinition> getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions();
    }

    @GetMapping("/gateway/services")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }
}
