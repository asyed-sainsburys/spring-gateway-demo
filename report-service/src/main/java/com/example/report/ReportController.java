package com.example.report;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReportController {

  private final UserClient userClient;

  public ReportController(UserClient userClient) {
    this.userClient = userClient;
  }

  @GetMapping("/api/reports/summary")
  public Map<String, Object> summary() {
    List<Map<String, Object>> users = userClient.getUsers();
    long admins = users.stream().filter(u -> "ADMIN".equals(u.get("role"))).count();
    long regular = users.size() - admins;

    return Map.of(
        "totalUsers", users.size(),
        "admins", admins,
        "regularUsers", regular,
        "sampleUsers", users
    );
  }
}
