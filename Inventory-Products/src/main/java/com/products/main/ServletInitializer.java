package com.products.main;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/* This servlet initialiser allows for app configuration when the app is run, 
 * this is done by using a servlet container (as this app is run on WAR packaging- Web Application Resource) 
 * */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InventoryProductsApplication.class);
	}

}
