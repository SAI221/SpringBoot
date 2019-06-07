package com.bridgelabz.springbootform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class SpringBootLoginRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLoginRegistrationApplication.class, args);
	}

}
