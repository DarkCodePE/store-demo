package com.codmind.api_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDoc(){
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                        .name("Authorization")
                        .description("Token de autenticacion").modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()
                ))
                .select() // Que servicios van a ser instrospectados
                .apis(RequestHandlerSelectors.basePackage("com.codmind.api_order.controllers")) //Que Clases o que paquetes vamos a documentar
                .paths(PathSelectors.any()) //De todas la clases que URL te interesan
                .build()
                .apiInfo(getApiInfo());
    }
    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Order Service API",
                "Order Service API description",
                "1.0",
                "https://stacklycode.com/terms",
                new Contact("stacklycode","https://stacklycode.com/", "Nexuscode25@gmail.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
