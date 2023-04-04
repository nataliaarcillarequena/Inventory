package com.products.service;

import java.util.List;

import com.products.entity.Product;

public interface ProductService {

	//returns a list of all the products from the product table
	public List<Product> getAllProducts();
	
	//returns the product object via the product id 
	public Product getProductByProdId(int productId);
	
	//inserts product object into the product table
	public boolean addProduct(Product product);
	
	//updates stock of a product, for reording items
	public boolean reorderItems(int prodId, int amount);
	
	//deleting a product by product Id
	public boolean deleteItem(int prodId);
	
	//searches for product via a keyword
	public List<Product> searchItems(String input);
	
}
