package com.inventory.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.inventory.entity.Inventory;
import com.inventory.entity.Product;

public interface InventoryService {

	//return the current inventory (current-today: 30th March 2023)
	public List<Inventory> getCurrentInventory(LocalDate date);
	
	//return the inventory list between 2 dates 
	public List<Inventory> salesBetweenDates(LocalDate start, LocalDate end);
	
	//add a product functionality 
	public boolean addProduct(Product product);
	
	//delete a product functionality 
	public String deleteProduct(int prodId);
	
	//reorder a product functionality
	public boolean reorderProduct(int prodId, int amount);
	
	//search for products in current inventory 
	public List<Inventory> searchInventory(String input);
	
	//return a product from the product dataset via productId
	public Product getProduct(int prodId);
	
	//aggregating sales data to plot on a time series
	public Map<String, Map<LocalDate, Integer>> aggreggatedData();
	
}
