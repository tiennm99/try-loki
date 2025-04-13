package com.miti99.loki;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.ResourceAttributes;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Configure OpenTelemetry
        OpenTelemetry openTelemetry = configureOpenTelemetry();

        // Start logging
        logger.info("Application started");

        // Run logging example
        LoggingExample loggingExample = new LoggingExample();
        loggingExample.performLogging();


        // Generate some additional logs
        generateSampleLogs();

        logger.info("Application stopping");
    }

    private static OpenTelemetry configureOpenTelemetry() {
        // Define service resource
        Resource resource = Resource.getDefault()
                .merge(Resource.create(Attributes.of(
                        ResourceAttributes.SERVICE_NAME, "opentelemetry-log4j2-alloy",
                        ResourceAttributes.SERVICE_VERSION, "1.0.0"
                )));

        // Configure OtlpGrpcLogRecordExporter - this will send logs to Alloy
        OtlpGrpcLogRecordExporter logExporter = OtlpGrpcLogRecordExporter.builder()
                .setEndpoint("http://localhost:4317") // Alloy OTLP endpoint
                .build();

        // Create SDK LoggerProvider
        SdkLoggerProvider loggerProvider = SdkLoggerProvider.builder()
                .setResource(resource)
                .addLogRecordProcessor(BatchLogRecordProcessor.builder(logExporter)
                        .setScheduleDelay(100, TimeUnit.MILLISECONDS)
                        .build())
                .build();

        // Build OpenTelemetry SDK
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
                .setLoggerProvider(loggerProvider)
                .build();

        // Register a shutdown hook to close the SDK when the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(loggerProvider::close));

        return openTelemetrySdk;
    }

    private static void generateSampleLogs() {
        // Generate various log levels
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");

        // Generate a log with an exception
        try {
            throw new RuntimeException("Simulated exception");
        } catch (Exception e) {
            logger.error("Exception occurred during processing", e);
        }

        // Generate logs with different contexts
        logger.info("User login successful", () -> "userId: 12345");
        logger.info("Payment processed", () -> "amount: $199.99, orderId: ORD-98765");

        // Add a slight delay to make logs more readable in the UI
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
