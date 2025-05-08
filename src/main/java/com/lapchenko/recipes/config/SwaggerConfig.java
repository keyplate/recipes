package com.lapchenko.recipes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Recipe API")
                        .version("1.0.0")
                        .description("This is an API documented for recipe REST with Swagger and SpringDoc.")
        );
    }
}
