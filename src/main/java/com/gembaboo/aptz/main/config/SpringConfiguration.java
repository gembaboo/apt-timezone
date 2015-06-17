package com.gembaboo.aptz.main.config;

import com.gembaboo.aptz.fileloader.CsvToMongoRouteBuilder;
import com.gembaboo.aptz.resources.AirportFileResource;
import org.apache.camel.RoutesBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.gembaboo.aptz.main.beans", "com.gembaboo.aptz.main.config", "com.gembaboo.aptz.fileloader", "com.gembaboo.aptz.resources"})
@Import(Jmx.class)
@EnableJpaRepositories(basePackages = "com.gembaboo.aptz.resources")
@EntityScan(basePackages = "com.gembaboo.aptz.domain")
public class SpringConfiguration {


    @Bean
    RoutesBuilder csvToMongoRouteBuilder() {
        CsvToMongoRouteBuilder csvToMongoRouteBuilder = new CsvToMongoRouteBuilder();
        csvToMongoRouteBuilder.setFile(new File(AirportFileResource.UPLOAD_DIR));
        return csvToMongoRouteBuilder;
    }

}
