package com.example.quartzexample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleDataDownloadingService implements DataDownloadingService {
    @Override
    public void executeSampleJob() {
        log.info("DOWNLOADING DATA...");
    }
}
