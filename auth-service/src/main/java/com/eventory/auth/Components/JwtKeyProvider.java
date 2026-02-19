package com.eventory.auth.Components;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

@Component
public class JwtKeyProvider {

  private final RSAKey rsaJwk;

  public JwtKeyProvider(
      @Value("${auth.jwt.key-id:auth-key-1}") String keyId,
      @Value("${auth.jwt.private-key}") String privateKeyBase64,
      @Value("${auth.jwt.public-key}") String publicKeyBase64
  ) {
    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");

      byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64.replaceAll("\\s+", ""));
      RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

      byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64.replaceAll("\\s+", ""));
      RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

      this.rsaJwk = new RSAKey.Builder(publicKey)
          .privateKey(privateKey)
          .keyUse(KeyUse.SIGNATURE)
          .algorithm(JWSAlgorithm.RS256)
          .keyID(keyId)
          .build();

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new IllegalStateException("Failed to init RSA keys", e);
    }
  }

  public RSAKey getRsaJwk() {
    return rsaJwk;
  }

  public JWKSet getPublicJwkSet() {
    return new JWKSet(rsaJwk.toPublicJWK());
  }
}
