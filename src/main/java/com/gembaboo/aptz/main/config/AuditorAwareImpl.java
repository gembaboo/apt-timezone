package com.gembaboo.aptz.main.config;

import org.springframework.data.domain.AuditorAware;

/**
 * Auditor provider
 *
 *
 * <a href="http://docs.spring.io/spring-data/data-mongo/docs/1.4.2.RELEASE/reference/html/mongo.core.html#mongo.auditing">
 *     http://docs.spring.io/spring-data/data-mongo/docs/1.4.2.RELEASE/reference/html/mongo.core.html#mongo.auditing</a>
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        return System.getProperty("user.name");
    }

}