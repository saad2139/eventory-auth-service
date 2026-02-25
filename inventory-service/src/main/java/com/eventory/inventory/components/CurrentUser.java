package com.eventory.inventory.components;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    
    private Jwt getJwt(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() instanceof Jwt jwt)
            return jwt;
        throw new IllegalStateException("User is not authenticated");

    }
    
    public UUID getTenantId(){
        String tenantId = getJwt().getClaimAsString("tenant_id");
        if(tenantId == null || tenantId.isBlank())
            throw new IllegalStateException("No tenant_id found in JWT");
        
        return UUID.fromString(tenantId);
    }

    public UUID getUserId() {
        String userId = getJwt().getSubject();
        if (userId == null)
            throw new IllegalStateException("No userId found in JWT");
        return UUID.fromString(userId);
    }

    public String getRole() {
        return getJwt().getClaimAsString("role");
    }

    public String getIssuer() {
        var issuer = getJwt().getIssuer();
        return issuer != null ? issuer.toString() : null;
    }

}
