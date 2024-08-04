package com.arenema.codingtest;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class CodingtestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodingtestApplication.class, args);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
