package com.example.backend.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author XXX
 * @since 2018-04-12
 */
@Component
public class PullNewsJob {

    private static final Logger logger = LoggerFactory.getLogger(PullNewsJob.class);
    @Autowired
    NewsPuller newsPuller;

    @Scheduled(fixedDelay = 1000)
    public void pullContent() {
        newsPuller.pullNews();
        newsPuller.pullCars();
        newsPuller.pullEntertain();
        newsPuller.pullIt();
        newsPuller.pullSports();
    }

}
