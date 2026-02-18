package com.eventory.auth.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventory.auth.Components.JwtKeyProvider;
import com.nimbusds.jose.jwk.JWKSet;

@RestController
public class JwksController {

  private final JWKSet jwkSet;

  public JwksController(JwtKeyProvider keyProvider) {
    this.jwkSet = keyProvider.getPublicJwkSet();
  }

  @GetMapping("/oauth2/jwks")
  public Map<String, Object> keys() {
    return jwkSet.toJSONObject();
  }
}
