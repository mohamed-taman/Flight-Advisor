package org.siriusxi.htec.fa;

import lombok.extern.log4j.Log4j2;
import org.siriusxi.htec.fa.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class FlightAdvisorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightAdvisorApiApplication.class, args);
	}
	
	@Component
	@Log4j2
	static class AppStartupRunner implements ApplicationRunner {
		
		private final UserRepository userRepository;
		
		public AppStartupRunner(UserRepository userRepository) {
			this.userRepository = userRepository;
		}
		
		@Override
		@Transactional
		public void run(ApplicationArguments args) throws Exception {
			
			userRepository.findAll().forEach(user -> log.info("User data: {}", user) );
			
		}
	}
}
