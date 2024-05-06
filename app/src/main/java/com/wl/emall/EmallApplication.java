package com.wl.emall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
@EnableScheduling
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class EmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmallApplication.class, args);
    }

}