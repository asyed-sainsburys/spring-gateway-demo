package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .authorizeHttpRequests(auth -> auth
                        // /login/** and /oauth2/** MUST be permitted to avoid the redirect loop:
                        // the OAuth2 callback (/login/oauth2/code/azure) would otherwise
                        // be intercepted, trigger another redirect to Azure AD, and loop forever.
                        .requestMatchers(
                                "/login/**",
                                "/oauth2/**",
                                "/public/**",
                                "/actuator/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/azure")
                        )
                )
                // oauth2Login: backend handles the full code exchange and issues JSESSIONID cookie.
                // NOTE: oauth2ResourceServer / JWT is intentionally removed — it conflicts with
                // session-based auth and causes the redirect loop.
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(new org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService() {
                                    @Override
                                    public org.springframework.security.oauth2.core.oidc.user.OidcUser loadUser(
                                            org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest userRequest) {
                                        return new org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser(
                                                userRequest.getAccessToken().getScopes().stream()
                                                        .map(scope -> new org.springframework.security.core.authority.SimpleGrantedAuthority("SCOPE_" + scope))
                                                        .toList(),
                                                userRequest.getIdToken()
                                        );
                                    }
                                })
                        )
                        .successHandler((request, response, authentication) ->
                                response.sendRedirect("http://localhost:3000")
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.sendRedirect("http://localhost:3000")
                        )
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}