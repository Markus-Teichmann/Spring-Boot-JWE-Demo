package com.springbootjwedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJweDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJweDemoApplication.class, args);
//		try {
//            JweConfig.generateRSAKey();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
	}

}
