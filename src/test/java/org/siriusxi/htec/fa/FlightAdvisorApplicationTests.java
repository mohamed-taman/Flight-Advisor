package org.siriusxi.htec.fa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FlightAdvisorApplicationTests {
    
    @Autowired
    private ApplicationContext context;
    
    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }
    
}
