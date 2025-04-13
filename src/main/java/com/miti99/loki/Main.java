package com.miti99.loki;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

  public static void main(String[] args) {
    LogGenerator logGenerator = new LogGenerator();

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(logGenerator::generateLogs, 0, 1, TimeUnit.SECONDS);

    log.info("Application started. Press Ctrl+C to stop.");
  }
}
