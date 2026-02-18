package com.eventory.auth.Services;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eventory.auth.Components.JwtKeyProvider;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtIssuer {

  private final JwtKeyProvider keyProvider;
  private final String issuer;
  private final String audience;
  private final long ttlSeconds;

  public JwtIssuer(
      JwtKeyProvider keyProvider,
      @Value("${auth.jwt.issuer}") String issuer,
      @Value("${auth.jwt.audience:platform}") String audience,
      @Value("${auth.jwt.ttl-seconds:900}") long ttlSeconds
  ) {
    this.keyProvider = keyProvider;
    this.issuer = issuer;
    this.audience = audience;
    this.ttlSeconds = ttlSeconds;
  }

  public String issueAccessToken(UUID userId, UUID tenantId, String role) {
    try {
      Instant now = Instant.now();
      Instant exp = now.plusSeconds(ttlSeconds);

      JWTClaimsSet claims = new JWTClaimsSet.Builder()
          .subject(userId.toString())
          .issuer(issuer)
          .audience(audience)
          .issueTime(Date.from(now))
          .expirationTime(Date.from(exp))
          .claim("tenant_id", tenantId.toString())
          .claim("role", role)
          .build();

      JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
          .keyID(keyProvider.getRsaJwk().getKeyID())
          .type(JOSEObjectType.JWT)
          .build();

      SignedJWT jwt = new SignedJWT(header, claims);
      jwt.sign(new RSASSASigner(keyProvider.getRsaJwk().toPrivateKey()));

      return jwt.serialize();
    } catch (JOSEException e) {
      throw new IllegalStateException("Failed to sign JWT", e);
    }
  }

  public long getTtlSeconds() {
    return ttlSeconds;
  }
}
