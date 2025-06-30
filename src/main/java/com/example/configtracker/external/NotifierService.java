package com.example.configtracker.external;

import com.example.configtracker.model.ConfigChange;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotifierService {

    private static final Logger logger = LoggerFactory.getLogger(NotifierService.class);

    @Retry(name = "externalNotifier", fallbackMethod = "fallback")
    public void notifyExternalSystem(ConfigChange change) {
        logger.warn("Notifying external system about: {}", change.getDescription());

        // Simulate failure for test
        if (change.getDescription().toLowerCase().contains("fail")) {
            throw new RuntimeException("Simulated failure");
        }

        logger.info("Notification sent successfully");
    }

    // Fallback method must be public and match signature + Throwable
    public void fallback(ConfigChange change, Throwable t) {
        logger.error("!!! Fallback: Failed to notify external system for [{}]. Reason: {}", change.getId(), t.getMessage());
    }
}

