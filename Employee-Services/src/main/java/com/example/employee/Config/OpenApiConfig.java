package com.example.employee.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management API")
                        .description(
                            "REST API documentation for Employee Microservice. "
                            + "Supports CRUD, Search, Filter, Pagination and Sorting."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Employee Management Project")));
    }
}
