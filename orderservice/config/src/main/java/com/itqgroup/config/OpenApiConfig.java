package com.itqgroup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Конфигурация OpenApi.
 */
@Configuration
public class OpenApiConfig implements WebMvcConfigurer {
    private final BuildProperties buildProperties;

    public OpenApiConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    /**
     * Бин OpenApi.
     *
     * @return настроенного OpenApi
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Сервис заказов.")
                        .version(buildProperties.getVersion())
                        .description(
                                "Сервис оформления, просмотра заказов"));
    }

}
