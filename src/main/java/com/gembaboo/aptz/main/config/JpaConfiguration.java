package com.gembaboo.aptz.main.config;


import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring configuration bean to initiate JPA support.
 * Searches for @Entity classes using @EntityScan annotation.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.gembaboo.aptz.resources")
@EntityScan(basePackages = "com.gembaboo.aptz.domain")
public class JpaConfiguration {

}
