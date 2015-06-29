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


/**
 * Spring configuration for scheduling the update job using Quartz
 * See <a href="http://quartz-scheduler.org/">http://quartz-scheduler.org/</a>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.gembaboo.aptz.scheduling")
public class SchedulingConfiguration {

    // There must be at least 5 seconds between subsequent calls to Google Maps API
    public static final int MIN_DURATION_BETWEEN_CALLS = 5000;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "jobdetail")
    public JobDetailFactoryBean getJobDetail() {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setJobClass(ScheduledUpdate.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }

    @Bean(name = "trigger")
    public SimpleTriggerFactoryBean getTrigger() {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setName("UPDATE_AIRPORTS");
        factory.setRepeatInterval(MIN_DURATION_BETWEEN_CALLS);
        factory.setJobDetail(getJobDetail().getObject());
        return factory;
    }

    @Bean(name = "scheduler")
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

    /**
     * Initializes the quartz jobstore in the default database.
     * @return
     */
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
