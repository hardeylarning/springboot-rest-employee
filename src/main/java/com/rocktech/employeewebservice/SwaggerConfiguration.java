package com.rocktech.employeewebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    public static Contact SUPPORTED_CONTACT = new Contact(
            "Roqeeb",
            "http://rocktech.com",
            "hardeylarning@gmail.com"
    );
    @Bean
    public Docket newApi () {
        Set produce_consume = new HashSet(Arrays.asList("application/json", "application/xml"));
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(produce_consume)
                .consumes(produce_consume);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Employee Status")
                .description("Employee Service with Swagger Documentation")
                .termsOfServiceUrl("http://localhost:8080/terms")
                .contact(SUPPORTED_CONTACT)
                .license("Employee license version")
                .licenseUrl("http://www.rocktech.com/license")
                .version("3.0")
                .build();
    }
}
