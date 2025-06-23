package com.vivek.firstmed.doctor_service.config;

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
                .title("Doctor Service API")
                .version("1.0")
                .description("API documentation for doctor microservice")
                .contact(new Contact().name("FirstMed").email("vivekbisht309@gmail.com"))
            );
    }
}
