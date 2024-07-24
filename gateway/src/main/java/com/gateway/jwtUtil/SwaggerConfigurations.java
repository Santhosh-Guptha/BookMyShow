package com.gateway.jwtUtil;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfigurations {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                    .title("API-GATEWAY")
                    .version("0.0.1-snapshot")
                    .description("Documentation on Api-Gateway"));
    }

}
