package com.bluescript.bank.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableIntegration
@ComponentScan("com.bluescript.bank.*")
public class Testjf10Application {

    public static void main(String[] args) {
        SpringApplication.run(Testjf10Application.class, args);
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

}