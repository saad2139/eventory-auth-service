package com.eventory.auth.Services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.eventory.auth.Config.AuthRegistrationConfig;
import com.eventory.auth.Entities.Tenant;
import com.eventory.auth.Entities.User;
import com.eventory.auth.Entities.UserRole;
import com.eventory.auth.Repos.TenantRepository;
import com.eventory.auth.Repos.UserRepository;
import com.eventory.auth.Repos.UserRoleRepository;


@Service
public class RegistrationService {
      private final UserRepository userRepo;
  private final UserRoleRepository roleRepo;
  private final TenantRepository tenantRepo;
  private final PasswordEncoder encoder;
  private final JwtIssuer issuer;
    
    private final AuthRegistrationConfig defaultTenantName;

    public RegistrationService(
        UserRepository userRepo,
        UserRoleRepository roleRepo,
        TenantRepository tenantRepo,
        PasswordEncoder encoder,
        JwtIssuer issuer,
        AuthRegistrationConfig defaultTenantName
    ){
        this.userRepo=userRepo;
        this.roleRepo=roleRepo;
        this.tenantRepo=tenantRepo;
        this.encoder=encoder;
        this.issuer=issuer;
        this.defaultTenantName=defaultTenantName;
    }

    @Transactional
    public String register(String email, String password){
        Tenant tenant = tenantRepo.findByName(defaultTenantName.defaultTenantName()).orElseThrow(()->new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Default tenant not set"));

        //Check if user exists
        if(userRepo.findByEmail(email).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        
        //Create new user
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setStatus("ACTIVE");
        user.setTenantId(tenant.getId());

        //Login after successful registration
        try {
            userRepo.save(user);
            UserRole role = new UserRole();
            role.setRole("CUSTOMER");
            role.setUserId(user.getId());
            roleRepo.save(role);

            return issuer.issueAccessToken(user.getId(),user.getTenantId(),"CUSTOMER");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

    }
}
