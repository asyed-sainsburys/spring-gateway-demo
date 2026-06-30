package com.example.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
public class ReportServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ReportServiceApplication.class, args);
  }

  @Bean
  RestClient restClient() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(Duration.ofSeconds(5));
    requestFactory.setReadTimeout(Duration.ofSeconds(10));

    return RestClient.builder()
        .requestFactory(requestFactory)
        .build();
  }
}