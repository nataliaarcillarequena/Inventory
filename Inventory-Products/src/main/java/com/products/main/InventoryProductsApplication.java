package com.products.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.products")
@EnableJpaRepositories(basePackages = "com.products.persistence")
@EntityScan(basePackages = "com.products.entity")
public class InventoryProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryProductsApplication.class, args);
	}
	
	/*bean for the rest template resource (to test insert/delete/etc functionality)
	 * found inside of the resource package 
	 */
	@Bean
	public RestTemplate gettemplate() {
		return new RestTemplate();
	}

}
