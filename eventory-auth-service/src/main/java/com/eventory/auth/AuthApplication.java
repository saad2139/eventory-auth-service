package com.eventory.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.eventory.auth.Config.AuthRegistrationConfig;

@SpringBootApplication
@EnableConfigurationProperties(AuthRegistrationConfig.class)
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
