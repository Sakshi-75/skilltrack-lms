package com.skilltrack.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI skillTrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SkillTrack LMS API")
                        .description("Learning Management System REST API")
                        .version("0.0.1"));
    }
}
