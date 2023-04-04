package com.inventory.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.inventory")
public class InventoryMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryMvcApplication.class, args);
	}
	
	//bean for the rest template resource (to interact with the product and sales microservices)
	@Bean
	public RestTemplate gettemplate() {
		return new RestTemplate();
	}

}
