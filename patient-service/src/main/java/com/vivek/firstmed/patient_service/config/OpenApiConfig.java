package com.vivek.firstmed.patient_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Patient Service API")
                .version("1.0")
                .description("API documentation for patient microservice")
                .contact(new Contact().name("FirstMed").email("vivekbisht3009@gmail.com"))
            );
    }
}
