package com.gembaboo.aptz.main.config;

import com.gembaboo.aptz.fileloader.AirportFileLoader;
import com.gembaboo.aptz.fileloader.FileLoader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.gembaboo.aptz.main.beans", "com.gembaboo.aptz.main.config", "com.gembaboo.aptz.resources"})
@Import(Jmx.class)
@EnableJpaRepositories(basePackages = "com.gembaboo.aptz.resources")
@EnableMongoRepositories(basePackages = "com.gembaboo.aptz.resources")
@EntityScan(basePackages = "com.gembaboo.aptz.domain")
public class SpringConfiguration {


    @Bean
    public FileLoader getFileLoader(){
        return new AirportFileLoader();
    }

}
