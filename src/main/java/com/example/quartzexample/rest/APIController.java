package com.example.quartzexample.rest;

import com.example.quartzexample.scheduled.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class APIController {
    @Autowired
    SchedulerService schedulerService;

    @GetMapping("/scheduler/restore")
    public void restore() {
        schedulerService.rescheduleDownloadingData();
    }
}
