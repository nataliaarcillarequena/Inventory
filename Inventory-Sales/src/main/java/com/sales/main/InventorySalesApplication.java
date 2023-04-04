package com.sales.main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.sales.entity.Sale;

/**These are the Spring boot calls which wire the layers 
 * of this application together. Spring searches the specified packages and 
 * executes this microservice of the application (running on localhost:8081).
 * This microservice is the Sales service which interacts with the Sale dataset 
 * in MySQL in order to retrieve the sales records (for the current day, which we 
 * are assuming to be 30th march as this is the last time data was added to 
 * the dataset, and over other desired time periods). The functionality provided
 * by this service is then handed to the Model View Controller service via the
 * REST API and used on the user interface, for the user to interact with on
 * the web app  */
@SpringBootApplication(scanBasePackages = "com.sales")
@EntityScan(basePackages = "com.sales.entity")
@EnableJpaRepositories(basePackages = "com.sales.persistence")
public class InventorySalesApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(InventorySalesApplication.class, args);
		
	}
	
	/*bean for the rest template resource (to test insert/delete/etc functionality)
	 * found inside of the resource package 
	 */
	@Bean
	public RestTemplate gettemplate() {
		return new RestTemplate();
	}

}
