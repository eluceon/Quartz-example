package com.example.quartzexample.scheduled;

import com.example.quartzexample.config.ScheduledConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@Service
public class SchedulerService {
    @Value("${download.data.cron}")
    private String downloadCron;

    private final Scheduler scheduler;
    private final JobDetail jobDetail;

    @Autowired
    public SchedulerService(Scheduler scheduler, JobDetail jobDetail) {
        this.scheduler = scheduler;
        this.jobDetail = jobDetail;
    }

    public void rescheduleDownloadingData() {
        try {
            CronTriggerImpl cronTrigger = new CronTriggerImpl();
            try {
                log.warn("Rescheduling with cron {}", downloadCron);
                cronTrigger.setCronExpression(downloadCron);
                cronTrigger.setName(ScheduledConfiguration.TRIGGER_IDENTITY);
            } catch (ParseException e) {
                log.error("Parse restore cron error: ", e);
            }
            scheduler.clear();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error("Rescheduler restore error: ", e);
        }
    }
}
