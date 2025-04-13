package com.miti99.loki;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.Random;

public class LoggingExample {
    private static final Logger logger = LogManager.getLogger(LoggingExample.class);
    private final Random random = new Random();

    public void performLogging() {
        logger.info("Starting logging example");

        // Using MDC (Mapped Diagnostic Context) with ThreadContext
        ThreadContext.put("requestId", generateRequestId());
        ThreadContext.put("userId", "user-" + random.nextInt(1000));

        try {
            // Simulate a series of operations with logging
            simulateUserAuthentication();
            simulateDataProcessing();
            simulateApiCall();
        } finally {
            // Clean up ThreadContext
            ThreadContext.clearAll();
        }

        logger.info("Completed logging example");
    }

    private void simulateUserAuthentication() {
        logger.info("User authentication started");

        // Simulate processing time
        sleep(200);

        if (random.nextBoolean()) {
            logger.info("User authentication successful");
        } else {
            logger.warn("User authentication required additional verification");
            sleep(300);
            logger.info("Secondary authentication successful");
        }
    }

    private void simulateDataProcessing() {
        logger.info("Data processing started");

        // Simulate processing time
        sleep(500);

        // Log processing metrics
        int recordsProcessed = random.nextInt(1000) + 100;
        double processingTime = random.nextDouble() * 400;

        logger.info("Processed {} records in {} ms", recordsProcessed, String.format("%.2f", processingTime));

        // Occasionally log an error
        if (random.nextInt(10) < 2) {
            logger.error("Failed to process some records due to data corruption");
        }
    }

    private void simulateApiCall() {
        logger.info("External API call initiated");

        // Simulate API call latency
        int latency = random.nextInt(1000) + 100;
        sleep(latency);

        // Log response status
        int statusCode = random.nextInt(10) < 8 ? 200 : 500;

        if (statusCode == 200) {
            logger.info("API call successful with latency {} ms", latency);
        } else {
            logger.error("API call failed with status code {}", statusCode);
            sleep(300);
            logger.info("Retrying API call");
            statusCode = random.nextInt(10) < 9 ? 200 : 500;

            if (statusCode == 200) {
                logger.info("Retry successful");
            } else {
                logger.error("Retry also failed with status code {}", statusCode);
            }
        }
    }

    private String generateRequestId() {
        return "req-" + Integer.toHexString(random.nextInt(0x1000000));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Sleep interrupted", e);
        }
    }
}
