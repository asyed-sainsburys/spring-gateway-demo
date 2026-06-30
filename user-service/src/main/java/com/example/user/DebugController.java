package com.example.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @GetMapping("/debug")
    public String debug(org.springframework.security.core.Authentication authentication) {
        return authentication == null
                ? "No authentication"
                : authentication.getClass().getName();
    }
}
