package com.gembaboo.aptz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class AirportTimezoneServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(AirportTimezoneServiceApplication.class, args);
    }


}
