package org.siriusxi.htec.fa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FlightAdvisorApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlightAdvisorApiApplication.class, args);
  }

  @Component
  @Log4j2
  static class AppStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
      log.info("Congratulations, Flight Advisor System is Up & Running :) ");
    }
  }
}
