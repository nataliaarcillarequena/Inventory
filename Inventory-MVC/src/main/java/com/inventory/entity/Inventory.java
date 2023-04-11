package com.inventory.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

	/*
	 * this inventory entity is a composition of the fields
	 * from the Product and Sales objects and are the fields that 
	 * will be displayed in the current inventory table of the inventory
	 * management system, as well as the sales reports 
	 */
	private int productId;
	private LocalDate saleDate;
	private String productName, productCategory;
	private int salesAmount, stock;
}
