package com.example.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

  private static final List<Map<String, Object>> USERS = List.of(
      Map.of("id", 1, "name", "Ada", "role", "ADMIN"),
      Map.of("id", 2, "name", "Linus", "role", "USER"),
      Map.of("id", 3, "name", "Grace", "role", "USER")
  );

  private final RestClient restClient;

  public UserController() {
    this.restClient = RestClient.create();
  }

  @GetMapping("/api/users")
  public List<Map<String, Object>> users(org.springframework.security.core.Authentication authentication) {
    System.out.println(authentication);

    return USERS;
  }

  @GetMapping("/api/users/{id}")
  public Map<String, Object> userById(@PathVariable int id) {
    return USERS.stream()
        .filter(u -> ((Integer) u.get("id")) == id)
        .findFirst()
        .orElse(Map.of("id", id, "name", "UNKNOWN", "role", "UNKNOWN"));
  }

  @SuppressWarnings("unchecked")
  @GetMapping("/api/users/reports")
  public Map<String, Object> getUsersAndReports(
      org.springframework.security.core.Authentication authentication,
      @RequestHeader(value = "Authorization", required = false) String authorizationHeader
  ) {
    Map<String, Object> reports = restClient.get()
        .uri("http://localhost:8082/api/reports/summary")
        .headers(headers -> {
          if (authorizationHeader != null && !authorizationHeader.isBlank()) {
            headers.set("Authorization", authorizationHeader);
          }
        })
        .retrieve()
        .body(Map.class);

    if (reports == null) {
      reports = Map.of();
    }

    return Map.of(
        "users", users(authentication),
        "reports", reports
    );
  }
}
