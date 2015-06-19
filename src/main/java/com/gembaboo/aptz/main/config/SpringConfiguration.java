package com.gembaboo.aptz.main.config;

import com.gembaboo.aptz.fileloader.CsvToMongoRouteBuilder;
import com.gembaboo.aptz.resources.AirportFileResource;
import org.apache.camel.RoutesBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

/**
 * Main entry point for Spring IoC
 * Scans the same package for other configuration beans.
 */
@ComponentScan(basePackages = {"com.gembaboo.aptz.main.config"})
@EnableAutoConfiguration
public class SpringConfiguration {

    @Bean
    RoutesBuilder csvToMongoRouteBuilder() {
        CsvToMongoRouteBuilder csvToMongoRouteBuilder = new CsvToMongoRouteBuilder();
        csvToMongoRouteBuilder.setFile(new File(AirportFileResource.UPLOAD_DIR));
        return csvToMongoRouteBuilder;
    }
}
