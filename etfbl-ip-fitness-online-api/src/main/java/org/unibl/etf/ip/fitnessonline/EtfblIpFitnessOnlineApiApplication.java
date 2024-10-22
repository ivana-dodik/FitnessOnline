package org.unibl.etf.ip.fitnessonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@SpringBootApplication
public class EtfblIpFitnessOnlineApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtfblIpFitnessOnlineApiApplication.class, args);
    }

}
