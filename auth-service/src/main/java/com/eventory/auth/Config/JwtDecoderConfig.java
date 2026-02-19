package com.eventory.auth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.eventory.auth.Components.JwtKeyProvider;


@Configuration
public class JwtDecoderConfig {

  @Bean
  public JwtDecoder jwtDecoder(JwtKeyProvider jwtKeyProvider) throws Exception {
      return NimbusJwtDecoder.withPublicKey(jwtKeyProvider.getRsaJwk().toRSAPublicKey()).build();
    } 
  }
