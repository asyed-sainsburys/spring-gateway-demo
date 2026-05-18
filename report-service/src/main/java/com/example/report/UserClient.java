package com.example.report;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class UserClient {

  private final RestClient restClient;

  public UserClient(RestClient restClient) {
    this.restClient = restClient;
  }

  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> getUsers() {
    return restClient.get()
        .uri("http://localhost:8081/api/users")
        .retrieve()
        .body(List.class);
  }
}
