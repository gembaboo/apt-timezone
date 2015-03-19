package com.gembaboo.aptz;

import com.gembaboo.aptz.fileloader.AbstractFileLoader;
import com.gembaboo.aptz.fileloader.FileLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AirportLoader {

    @Bean
    FileLoader getFileLoader() {
        FileLoader fileLoader = AbstractFileLoader.createMongoFileLoader();
        return fileLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(AirportLoader.class, args);
    }


}
