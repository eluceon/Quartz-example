package com.example.quartzexample.config;

import com.example.quartzexample.scheduled.DataDownloadingJob;
import com.example.quartzexample.scheduled.AutoWiringSpringBeanJobFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;


@Configuration
public class ScheduledConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    private final static String JOB_IDENTITY = "DATA_DOWNLOADING_JOB";
    public final static String TRIGGER_IDENTITY = "DATA_DOWNLOADING_TRIGGER";

    @Value("${download.data.cron}")
    private String downloadCron;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail jobDetail) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(jobDetail);
        schedulerFactory.setTriggers(trigger);
        return schedulerFactory;
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(DataDownloadingJob.class);
        jobDetailFactory.setName(JOB_IDENTITY);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
        cronTriggerFactory.setJobDetail(jobDetail);
        cronTriggerFactory.setName(TRIGGER_IDENTITY);
        cronTriggerFactory.setCronExpression(downloadCron);
        return cronTriggerFactory;
    }

}
