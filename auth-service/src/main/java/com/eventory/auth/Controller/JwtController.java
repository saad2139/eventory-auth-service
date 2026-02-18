package com.eventory.auth.Controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identify")
public class JwtController {

  @GetMapping("/me")
  public Map<String, Object> me(Authentication auth) {
    System.out.println(auth);
    // We'll configure this app as a resource server too (for /auth/me)
    Jwt jwt = (Jwt) auth.getPrincipal();
    return Map.of(
        "sub", jwt.getSubject(),
        "tenant_id", jwt.getClaimAsString("tenant_id"),
        "role", jwt.getClaimAsString("role"),
        "iss", jwt.getIssuer() != null ? jwt.getIssuer().toString() : null,
        "aud", jwt.getAudience()
    );
  }
}
