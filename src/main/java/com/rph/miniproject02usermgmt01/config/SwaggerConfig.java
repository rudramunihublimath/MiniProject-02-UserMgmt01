package com.rph.miniproject02usermgmt01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rph.miniproject02usermgmt01"))
                .paths(PathSelectors.any()).build();
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("JavaInUse API")
                .description("API reference for developers")
                .termsOfServiceUrl("http://www.google.in")
                .contact(new Contact("RUDRAMUNI","Google.com","rudra.hublimath@gmail.com"))
                .license("Rudra License")
                .licenseUrl("rudra.hublimath@gmail.com")
                .version("1.0")
                .build();
    }
}
