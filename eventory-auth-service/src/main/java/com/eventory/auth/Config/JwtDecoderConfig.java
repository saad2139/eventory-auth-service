package com.eventory.auth.Config;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfig {

  @Bean
  public JwtDecoder jwtDecoder(@Value("${auth.jwt.public-key-pem}") Resource publicPem) throws Exception {
    RSAPublicKey publicKey = readPublicKey(publicPem);
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  private static RSAPublicKey readPublicKey(Resource resource) throws Exception {
    String pem;
    try (InputStream is = resource.getInputStream()) {
      pem = new String(is.readAllBytes());
    }

    pem = pem.replace("-----BEGIN PUBLIC KEY-----", "")
             .replace("-----END PUBLIC KEY-----", "")
             .replaceAll("\\s+", "");

    byte[] der = Base64.getDecoder().decode(pem);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(der);

    return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
  }
}
