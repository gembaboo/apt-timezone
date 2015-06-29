package com.gembaboo.aptz.main.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Spring configuration bean for the MongoDB support
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.gembaboo.aptz.resources")
@EnableMongoAuditing
public class MongoConfiguration extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "apt-timezone";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();
    }

    @Bean
    public AuditorAware<String> myAuditorProvider() {
        return new AuditorAwareImpl();
    }

}
