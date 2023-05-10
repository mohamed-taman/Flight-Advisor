package org.siriusxi.fa.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

@Configuration
public class SwaggerConfig {
    
    @Value("${app.version}")
    private String appVersion;
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                   .components(new Components()
                                   .addSecuritySchemes("bearer-key",
                                       new SecurityScheme()
                                           .type(HTTP)
                                           .scheme("bearer")
                                           .bearerFormat("JWT")))
                   .info(new Info()
                             .title("REST API for Flight Advisor Service.")
                             .version(this.appVersion)
                             .license(new License()
                                          .name("MIT License")
                                          .url("https://springdoc.org")));
    }
    
}
