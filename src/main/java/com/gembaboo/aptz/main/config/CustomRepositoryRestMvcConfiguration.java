package com.gembaboo.aptz.main.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Spring configuration for the customization for the Spring Data REST repositories
 */
@Configuration
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.data.rest")
    @Override
    public RepositoryRestConfiguration config() {
        RepositoryRestConfiguration config = super.config();
        config.setBaseUri("/data-api");
        return config;
    }
}
