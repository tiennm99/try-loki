package com.miti99.loki;

import java.util.Random;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogGenerator {

  private static final String[] SERVICES = {
    "auth-service",
    "user-service",
    "payment-service",
    "notification-service",
    "product-service",
    "order-service"
  };
  private static final String[] OPERATIONS = {
    "create", "read", "update", "delete", "process", "validate", "authenticate"
  };
  private static final String[] ENTITIES = {
    "user", "order", "payment", "product", "account", "session", "transaction"
  };

  private final Random random = new Random();
  private int requestCounter = 0;

  public void generateLogs() {
    String service = SERVICES[random.nextInt(SERVICES.length)];
    String operation = OPERATIONS[random.nextInt(OPERATIONS.length)];
    String entity = ENTITIES[random.nextInt(ENTITIES.length)];
    String level = getRandomLogLevel();

    requestCounter++;
    String requestId = String.format("REQ-%06d", requestCounter);

    switch (level) {
      case "INFO" ->
          log.info(
              "Service: {}, Operation: {} {}, RequestId: {}",
              service,
              operation,
              entity,
              requestId);

      case "DEBUG" ->
          log.debug(
              "Service: {}, Operation: {} {}, Details: {}, RequestId: {}",
              service,
              operation,
              entity,
              generateDetails(operation, entity),
              requestId);

      case "WARN" ->
          log.warn(
              "Service: {}, Operation: {} {}, Warning: {}, RequestId: {}",
              service,
              operation,
              entity,
              generateWarning(),
              requestId);

      case "ERROR" ->
          log.error(
              "Service: {}, Operation: {} {}, Error: {}, RequestId: {}",
              service,
              operation,
              entity,
              generateError(),
              requestId);
    }
  }

  private String getRandomLogLevel() {
    int rand = random.nextInt(100);
    if (rand < 60) {
      return "INFO";
    } else if (rand < 80) {
      return "DEBUG";
    } else if (rand < 95) {
      return "WARN";
    } else {
      return "ERROR";
    }
  }

  private String generateDetails(String operation, String entity) {
    return String.format(
        "%s %s with id=%d, took %dms",
        operation, entity, random.nextInt(10000), random.nextInt(500));
  }

  private String generateWarning() {
    String[] warnings = {
      "Operation taking longer than expected",
      "Resource utilization high",
      "Rate limit approaching",
      "Using deprecated API",
      "Retrying operation after temporary failure"
    };
    return warnings[random.nextInt(warnings.length)];
  }

  private String generateError() {
    String[] errors = {
      "Database connection failed",
      "Authentication failed",
      "Invalid input data",
      "Resource not found",
      "Service timeout",
      "Permission denied",
      "Rate limit exceeded"
    };
    return errors[random.nextInt(errors.length)];
  }
}
