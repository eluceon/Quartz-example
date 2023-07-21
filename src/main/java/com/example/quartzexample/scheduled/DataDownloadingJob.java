package com.example.quartzexample.scheduled;

import com.example.quartzexample.service.DataDownloadingService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataDownloadingJob implements Job {
    @Autowired
    private DataDownloadingService jobService;

    @Override
    public void execute(JobExecutionContext context) {
        jobService.executeSampleJob();
    }
}
