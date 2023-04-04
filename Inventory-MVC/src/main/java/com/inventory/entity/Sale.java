package com.inventory.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

	/* 
	 * for additional information on this Sale class, please
	 * see the entity package in the Sale microservice 
	 */
	
	private int productId;
	private LocalDate saleDate;
	private int salesAmount;
	
}
