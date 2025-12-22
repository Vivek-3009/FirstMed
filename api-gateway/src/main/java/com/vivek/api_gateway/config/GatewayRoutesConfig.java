package com.vivek.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
        .route("patient-service", r -> r.path("/patient/**")
        .filters(f -> f.stripPrefix(1))
        .uri("lb://patient-service"))
        .route("doctor-service", r -> r.path("/doctor/**")
        .filters(f -> f.stripPrefix(1))
        .uri("lb://doctor-service"))
        .route("appointment-service", r -> r.path("/appointment/**")
        .filters(f -> f.stripPrefix(1))
        .uri("lb://appointment-service"))
        .build();
        }

}
