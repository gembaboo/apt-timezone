package com.gembaboo.aptz;

import com.gembaboo.aptz.fileloader.FileLoader;
import com.gembaboo.aptz.fileloader.FileLoaderFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main {

    @Bean
    FileLoader getFileLoader() {
        FileLoader fileLoader = FileLoaderFactory.createMongoFileLoader();
        return fileLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}
