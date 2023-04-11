package com.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	/* 
	 * for additional information on this Product class, please
	 * see the entity package in the Product microservice 
	 */
	private int productId;
	private String productName, productCategory;
	private int stock;
	
}
