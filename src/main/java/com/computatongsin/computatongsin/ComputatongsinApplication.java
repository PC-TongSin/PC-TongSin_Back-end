package com.computatongsin.computatongsin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ComputatongsinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputatongsinApplication.class, args);
    }

}
