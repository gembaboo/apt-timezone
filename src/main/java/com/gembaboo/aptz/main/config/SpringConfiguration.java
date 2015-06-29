package com.gembaboo.aptz.main.config;

import com.gembaboo.aptz.fileloader.CsvToMongoRouteBuilder;
import com.gembaboo.aptz.resources.AirportFileResource;
import com.gembaboo.aptz.gateway.LocationTimeZone;
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

    /**
     * Camel route builder, to support the uploading and processing of the airport file.
     * @return
     */
    @Bean
    RoutesBuilder csvToMongoRouteBuilder() {
        CsvToMongoRouteBuilder csvToMongoRouteBuilder = new CsvToMongoRouteBuilder();
        csvToMongoRouteBuilder.setFile(new File(AirportFileResource.UPLOAD_DIR));
        return csvToMongoRouteBuilder;
    }

    /**
     * Service bean for getting the timezone for a location.
     * @return
     */
    @Bean
    LocationTimeZone getLocationTimeZone() {
        return new LocationTimeZone();
    }
}
