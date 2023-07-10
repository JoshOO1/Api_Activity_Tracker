package com.tracking.activitytracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ActivityTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityTrackingApplication.class, args);
    }

}
