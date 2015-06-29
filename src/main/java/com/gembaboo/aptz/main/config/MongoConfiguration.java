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
 * Spring configuration bean for the MongoDB support.
 * This configuration enables auditing, to understand how it works refer to
 * <a href="http://www.javabydefault.com/2014/03/how-to-set-createdby-createddate.html">
 *     http://www.javabydefault.com/2014/03/how-to-set-createdby-createddate.html</a>
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.gembaboo.aptz.resources", "com.gembaboo.aptz.scheduling"})
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
