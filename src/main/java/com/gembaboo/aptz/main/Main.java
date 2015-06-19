package com.gembaboo.aptz.main;

import com.gembaboo.aptz.main.config.SpringConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Bootstrapping of the Application using Spring Boot's SpringApplication
 */
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfiguration.class, args);
    }

}
