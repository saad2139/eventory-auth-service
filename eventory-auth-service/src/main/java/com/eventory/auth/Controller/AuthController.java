package com.eventory.auth.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eventory.auth.Dto.LoginRequest;
import com.eventory.auth.Dto.LoginResponse;
import com.eventory.auth.Entities.User;
import com.eventory.auth.Repos.UserRepository;
import com.eventory.auth.Repos.UserRoleRepository;
import com.eventory.auth.Services.JwtIssuer;
import com.eventory.auth.Services.RegistrationService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/auth")
public class AuthController {

private final UserRepository userRepo;
private final UserRoleRepository roleRepo;
private final PasswordEncoder encoder;
private final JwtIssuer jwtIssuer;
private final RegistrationService regService;
public AuthController(UserRepository userRepo,
                        UserRoleRepository roleRepo,
                        PasswordEncoder encoder,
                        JwtIssuer jwtIssuer,
                        RegistrationService regService) {
    this.userRepo = userRepo;
    this.roleRepo = roleRepo;
    this.encoder = encoder;
    this.jwtIssuer = jwtIssuer;
    this.regService = regService;
  }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
       User user = userRepo.findByEmail(req.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        
        if(!"ACTIVE".equalsIgnoreCase(user.getStatus()))
             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User inactive");
        
        if(!encoder.matches(req.password(), user.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");

        
        List<String> userRoles = roleRepo.findByUserId(user.getId()).stream().map((r)->r.getRole()).toList();

        if (userRoles.isEmpty())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No role assigned");
        
        String role = userRoles.get(0);
        String token  = jwtIssuer.issueAccessToken(user.getId(), user.getTenantId(), role);

        return new LoginResponse(token, "Bearer",jwtIssuer.getTtlSeconds());    
    
}

@PostMapping("/register")
public LoginResponse Register(@Valid @RequestBody LoginRequest req) {

    String token = this.regService.register(req.email(), req.password());
    return new LoginResponse(token, "Bearer", jwtIssuer.getTtlSeconds());
}

}
