package com.eventory.inventory.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collection;
import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken>{
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Extract role from JWT claims
        String role = jwt.getClaimAsString("role");
        
        // Create authorities from role
        Collection<GrantedAuthority> authorities = role != null 
            ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
            : Collections.emptyList();

        // Create custom authentication token with tenant_id and userId available
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
