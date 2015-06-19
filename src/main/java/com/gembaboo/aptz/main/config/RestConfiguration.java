package com.gembaboo.aptz.main.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration bean to ignite REST services in the corresponding package(s)
 */
@Configuration
@ComponentScan(basePackages = {"com.gembaboo.aptz.resources"})
public class RestConfiguration {

}
