package com.gembaboo.aptz.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;

/**
 * Spring configuration bean for JMX support
 */
@Configuration
public class Jmx {

    @Bean
    @Lazy(false)
    public MBeanExporter getMBeanExporter() {
        return new AnnotationMBeanExporter();
    }

    @Bean
    public MetadataNamingStrategy getNamingStrategy() {
        MetadataNamingStrategy strategy = new MetadataNamingStrategy();
        strategy.setAttributeSource(new AnnotationJmxAttributeSource());
        return strategy;
    }

    @Bean
    public MetadataMBeanInfoAssembler getMbeanInfoAssembler() {
        return new MetadataMBeanInfoAssembler(new AnnotationJmxAttributeSource());
    }
}
