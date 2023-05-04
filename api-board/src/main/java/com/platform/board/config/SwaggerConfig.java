package com.platform.board.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Spring Cloud Example API 명세서")
                    .description("게시판 API 호출 명세")
                    .version("v0.0.1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
