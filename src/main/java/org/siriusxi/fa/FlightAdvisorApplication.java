package org.siriusxi.fa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Starting point of Flight Advisor Application.
 *
 * @author Mohamed Taman
 * @version 1.0
 */
@SpringBootApplication
@Log4j2
public class FlightAdvisorApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FlightAdvisorApplication.class, args);
    }
    
    @Bean
    public ApplicationRunner atStartup() {
        return args -> log.info("Flight Advisor Application is Up & Running... ");
    }
}
