package com.eventory.auth.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="auth.registration")
public record AuthRegistrationConfig(String defaultTenantName) {
    
}
