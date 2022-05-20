package com.ebono.bonosapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

//url: http://localhost:8081/api/v1/swagger-ui.html#/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "E Bono Corp API",
                "This API provides all methods required for bonos management",
                "1.0",
                "TERMS OF SERVICE URL",
                new Contact("Richard Maguiña","https://github.com/ZuxAlone","u202021097@upc.edu.pe"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}