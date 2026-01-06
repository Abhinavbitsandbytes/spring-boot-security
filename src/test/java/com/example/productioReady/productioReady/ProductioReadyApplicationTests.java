package com.example.productioReady.productioReady;

import com.example.productioReady.productioReady.entities.UserEntity;
import com.example.productioReady.productioReady.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductioReadyApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		UserEntity user = new UserEntity(4L, "Abhi@gmail.com", "1234");
		String token = jwtService.generateToken(user);
		System.out.println("Generated Token: " + token); // Generated Token: eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJBYmhpQGdtYWlsLmNvbSIsInJvbGUiOlsiQURNSU4iLCJVU0VSIl0sImlhdCI6MTc2NzY5OTc1NSwiZXhwIjoxNzY3Njk5ODE1fQ.EfKp28VgkAcyMQw-95ECLkWGGf9sY_HaopAunKv8DqMDNPQwQ5R0kSrppiVf9PRK

		long userId = jwtService.getUserIdFromToken(token);
		System.out.println("Extracted User ID: " + userId); // Extracted User ID: 4
	}

}
