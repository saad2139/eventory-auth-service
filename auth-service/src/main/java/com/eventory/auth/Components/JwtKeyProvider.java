package com.eventory.auth.Components;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
      @Value("${auth.jwt.private-key-pem:classpath:keys/private.pem}") Resource privateKeyPem,
      @Value("${auth.jwt.public-key-pem:classpath:keys/public.pem}") Resource publicKeyPem
  ) {
    try {
      RSAPrivateKey privateKey = (RSAPrivateKey) parseKey(privateKeyPem, "PRIVATE KEY");
      RSAPublicKey publicKey = (RSAPublicKey) parseKey(publicKeyPem, "PUBLIC KEY");

      this.rsaJwk = new RSAKey.Builder(publicKey)
          .privateKey(privateKey)
          .keyUse(KeyUse.SIGNATURE)
          .algorithm(JWSAlgorithm.RS256)
          .keyID(keyId)
          .build();
    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new IllegalStateException("Failed to init RSA keys", e);
    }
  }

  public RSAKey getRsaJwk() {
    return rsaJwk;
  }

  public JWKSet getPublicJwkSet() {
    return new JWKSet(rsaJwk.toPublicJWK());
  }

  private Key parseKey(Resource pemResource, String keyType)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] decoded = parsePemContent(pemResource, keyType);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return switch (keyType) {
      case "PRIVATE KEY" -> keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
      case "PUBLIC KEY" -> keyFactory.generatePublic(new X509EncodedKeySpec(decoded));
      default -> throw new InvalidKeySpecException("Unsupported key type: " + keyType);
    };
  }

  private byte[] parsePemContent(Resource pemResource, String keyType) throws IOException {
    String pem = new String(pemResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    String beginMarker = "-----BEGIN " + keyType + "-----";
    String endMarker = "-----END " + keyType + "-----";
    String normalized = pem
        .replace(beginMarker, "")
        .replace(endMarker, "")
        .replaceAll("\\s", "");
    return Base64.getDecoder().decode(normalized);
  }
}
