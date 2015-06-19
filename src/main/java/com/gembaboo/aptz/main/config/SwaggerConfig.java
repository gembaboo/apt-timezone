package com.gembaboo.aptz.main.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


/**
 * Swagger configuration bean
 * See <a href="http://springfox.github.io/springfox/docs/current/#configuring-springfox">http://springfox.github.io/springfox/docs/current/#configuring-springfox</a>
 */
@EnableSwagger2
@Configuration
@ComponentScan(basePackages = {"com.gembaboo.aptz.resources"})
public class SwaggerConfig {


    @Bean
    public Docket apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(or(regex("/file.*"),regex("/airport.*"), regex("/data-api.*"), regex("/shutdown")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Airport TimeZone API")
                .description("")
                .termsOfServiceUrl("http://gembaboo.com")
                .contact("Gembaboo OSS")
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .version("2.0")
                .build();
    }

}