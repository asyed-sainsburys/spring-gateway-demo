package com.example.report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class UserClient {

  private final RestClient restClient;
  private final String usersUri;

  public UserClient(
      RestClient restClient,
      @Value("${app.user-service.users-uri:http://localhost:8081/api/users}") String usersUri
  ) {
    this.restClient = restClient;
    this.usersUri = usersUri;
  }

  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> getUsers(String authorizationHeader) {
    return restClient.get()
        .uri(usersUri)
        .headers(headers -> {
          if (authorizationHeader != null && !authorizationHeader.isBlank()) {
            headers.set("Authorization", authorizationHeader);
          }
        })
        .retrieve()
        .body(List.class);
  }

  public List<Map<String, Object>> getUsersWithAccessToken(String accessToken) {
    return getUsers("Bearer " + accessToken);
  }
}
