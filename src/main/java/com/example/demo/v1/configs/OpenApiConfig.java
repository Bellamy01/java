package com.example.demo.v1.configs;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(
                        name = "Biramahire Dan bellamy",
                        email = "bdanbellamy@gmail.com",
                        url = "https://github.com/Bellamy01"
                )
        )
)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi(
            @Value("${openapi.service.title}") String serviceTitle,
            @Value("${openapi.service.version}") String serviceVersion,
            @Value("${openapi.service.url}") String url
    ) {
        return new OpenAPI()
                .servers(List.of(new Server().url(url)))
                .components(new Components())
                .info(new Info().contact(new io.swagger.v3.oas.models.info.Contact().email("bdanbellamy@gmail.com").name("Dan Bellamy").url("https://github.com/Bellamy01")).title(serviceTitle).version(serviceVersion));
    }
}
