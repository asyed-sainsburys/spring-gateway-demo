package com.example.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class UsersSyncJob {

  private static final Logger log = LoggerFactory.getLogger(UsersSyncJob.class);
  private static final long INITIAL_DELAY_MS = 5_000L;
  private static final long FIXED_DELAY_MS = 30_000L;

  private final RestClient restClient;
  private final UserClient userClient;
  private final String tokenUri;
  private final String clientId;
  private final String clientSecret;
  private final String scope;

  public UsersSyncJob(
      RestClient restClient,
      UserClient userClient,
      @Value("${app.oauth2.token-uri}") String tokenUri,
      @Value("${app.oauth2.client-id}") String clientId,
      @Value("${app.oauth2.client-secret}") String clientSecret,
      @Value("${app.oauth2.scope:}") String scope
  ) {
    this.restClient = restClient;
    this.userClient = userClient;
    this.tokenUri = tokenUri;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.scope = scope;
  }

  @Scheduled(initialDelay = INITIAL_DELAY_MS, fixedDelay = FIXED_DELAY_MS)
  public void fetchUsersWithClientCredentials() {
    log.info("Users sync job triggered");
    try {
      String accessToken = fetchAccessToken();
      List<Map<String, Object>> users = userClient.getUsersWithAccessToken(accessToken);
      log.info("Users sync job fetched {} users: {}", users.size(), users);
    } catch (Exception ex) {
      log.error("Users sync job failed", ex);
    }
  }

  private String fetchAccessToken() {
    String payload = "grant_type=client_credentials"
        + "&client_id=" + encode(clientId)
        + "&client_secret=" + encode(clientSecret)
        + (scope == null || scope.isBlank() ? "" : "&scope=" + encode(scope));

    Map<?, ?> tokenResponse = restClient.post()
        .uri(tokenUri)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(payload)
        .retrieve()
        .body(Map.class);

    if (tokenResponse == null || tokenResponse.get("access_token") == null) {
      throw new IllegalStateException("Token endpoint did not return access_token");
    }
    return tokenResponse.get("access_token").toString();
  }

  private String encode(String value) {
    return UriUtils.encode(value, StandardCharsets.UTF_8);
  }
}

