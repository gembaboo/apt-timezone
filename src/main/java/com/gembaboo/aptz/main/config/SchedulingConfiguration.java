package com.gembaboo.aptz.main.config;

import com.gembaboo.aptz.scheduling.ScheduledUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.gembaboo.aptz.scheduling")
public class SchedulingConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public JobDetailFactoryBean getJobDetail() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setJobClass(ScheduledUpdate.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }

    @Bean
    public SimpleTriggerFactoryBean getTrigger() {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setName("UPDATE_AIRPORTS");
        factory.setRepeatInterval(1000 * 60 * 60 * 24);
        factory.setJobDetail(getJobDetail().getObject());
        return factory;
    }

    @Bean
    @DependsOn(value = "quartzDataInitializer")
    public SchedulerFactoryBean getScheduler() {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setTriggers(getTrigger().getObject());
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);
        return quartzScheduler;
    }

    @Bean(name = "quartzDataInitializer")
    public DataSourceInitializer getQuartzDataInitializer() {
        DataSourceInitializer quartzDataInitializer = new DataSourceInitializer();
        quartzDataInitializer.setDataSource(dataSource);
        quartzDataInitializer.setEnabled(true);
        quartzDataInitializer.setDatabasePopulator(getDatabasePopulator());
        return quartzDataInitializer;
    }


    @Bean
    public ResourceDatabasePopulator getDatabasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(true);
        resourceDatabasePopulator.setIgnoreFailedDrops(true);
        resourceDatabasePopulator.setSqlScriptEncoding("UTF-8");
        resourceDatabasePopulator.setScripts(new ClassPathResource("create-quartz-tables.sql"));
        return resourceDatabasePopulator;
    }
}
