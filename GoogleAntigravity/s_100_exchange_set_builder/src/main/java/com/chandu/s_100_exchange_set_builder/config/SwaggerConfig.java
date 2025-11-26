package com.chandu.s_100_exchange_set_builder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI s100OpenAPI() {
        return new OpenAPI()
                .info(new Info().title("S-100 Exchange Set Builder API")
                        .description("API for managing S-100 Exchange Sets")
                        .version("1.0"));
    }
}
