package com.ting.photoapp.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppApiUsers {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsers.class, args);
	}

	@Bean
	public BCryptPasswordEncoder createBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
