package com.example.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

  private static final List<Map<String, Object>> USERS = List.of(
      Map.of("id", 1, "name", "Ada", "role", "ADMIN"),
      Map.of("id", 2, "name", "Linus", "role", "USER"),
      Map.of("id", 3, "name", "Grace", "role", "USER")
  );

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
}
