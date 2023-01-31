/**
 * @author	: Rajiv Kumar
 * @project	: user-service
 * @since	: 0.0.1
 * @date	: 31-Jan-2023
 */

package com.github.ecominds.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserServiceAPIMainClass {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceAPIMainClass.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}