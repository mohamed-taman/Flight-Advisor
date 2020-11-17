package org.siriusxi.htec.fa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Starting point of Flight Advisor Application.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@SpringBootApplication
public class FlightAdvisorApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FlightAdvisorApplication.class, args);
    }
    
    @Component
    @Log4j2
    static class AppStartupRunner implements ApplicationRunner {
        
        @Override
        public void run(ApplicationArguments args) {
            log.info("Congratulations, Flight Advisor Application is Up & Running :) ");
        }
    }
}
