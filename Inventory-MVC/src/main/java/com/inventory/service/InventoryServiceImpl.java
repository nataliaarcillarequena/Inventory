package com.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventory.entity.Inventory;
import com.inventory.entity.Product;
import com.inventory.entity.Sale;

@Service
public class InventoryServiceImpl implements InventoryService {

	/* wiring the rest template in order to use the GET, POST, PUT, DELETE requests via the REST API,
	 * allowing interaction with the Sales and Products microservices
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * This service layer is not connected to a Dao layer as in the 
	 * product and sale microservcies, instead it interacts with those microservices
	 * via the REST API, providing all the functionality of the application
	 */
	
	//returns the current inventory (sales and products properties, sales for today)
	@Override
	public List<Inventory> getCurrentInventory(LocalDate date) {

		//get the list of all products (from product dataset) via the get Request of the REST API, putting the products an array with type product object
		Product[] allProducts = restTemplate.getForObject("http://localhost:8080/products/all", Product[].class);
		
		//initialising a list for inventory objects
		List<Inventory> currentInventory = new ArrayList<>();
		
		//iterating through the products list to find the corresponding items record (for todays date- 30th March) in the 
		//sales dataset then gathering information and adding to the Inventory List
		for(Product p:allProducts) {
			
			int prodId = p.getProductId();
			//get the sale object from sale dataset via prodId using REST API (as interacting with Inventory-Sale microservice)
			Sale sale = restTemplate.getForObject("http://localhost:8081/sales/"+prodId+"/"+date, Sale.class);
			
			//instantiate inventory object with information from the product and its corresponding sale
			//if there is no sale record with a specific product id (e.g. if we have added a new product- does 
			//not yet have any sales) then a null object is returned and set the sale amount to 0 in inventory object
			//default sales amount to 0- if not null object then set sales amount 
			Inventory inventoryItem = new Inventory(prodId, date, p.getProductName(), p.getProductCategory(),
					0, p.getStock());
			
			if(sale != null)
				inventoryItem.setSalesAmount(sale.getSalesAmount());
			
			//add the inventoryItem to the list of current inventory items
			currentInventory.add(inventoryItem);
			
		}
		
		return currentInventory;
	}

	//Adding product functionality: returns true if product successfully added to the MySQL product dataset 
	@Override
	public boolean addProduct(Product product) {
		
		//using post request (via rest template) to insert a new product into the product dataset
		//this returns "product added" if object is successfully inserted 
		String inserted = restTemplate.postForObject("http://localhost:8080/products", product, String.class);
		
		if(inserted.equals("product added"))
			return true;
		
		return false;
	}
	
	//deleting product functionality: returns true if product successfully deleted from the product dataset
	@Override
	public String deleteProduct(int prodId) {
		
		//need to set up headers and entity as using the exchange method in order to return string value 
		//from rest template when item is deleted from product table (restTemplate.delete does not allow
		//string type return- returns void)
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String deleted = restTemplate.exchange("http://localhost:8080/products/delete/"+prodId, HttpMethod.DELETE, entity, String.class).toString();
		
		return deleted;
	}
	
	/*restocking product by updating the stock in the product database,
	  returns true if product successfully reordered (stock successfully updated
	  by the desired quantity) */
	@Override
	public boolean reorderProduct(int prodId, int amount) {
		
		//using exchange method to update as put method returns type void 
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String updated = restTemplate.exchange("http://localhost:8080/products/"+prodId+"/"+amount, HttpMethod.PUT, entity, String.class).toString();
		
		//from the put request, there should be a string "stock updated" if quantity of product has 
		//been successfully updated via rest template
		if(updated.equals("stock updated"))
			return true;
		
		return false;
	}
	
	/*search for item in inventory functionality via users search input.
	  Looks for the search input in product name and product category and
	  returns a list of inventory objects, or null if no product found*/ 
	@Override
	public List<Inventory> searchInventory(String input){
		
		//get the array of Product objects from the product dataset that include the users input in 
		//the category/ name of the product 
		Product[] searchedProdcuts = restTemplate.getForObject("http://localhost:8080/products/search/"+input, Product[].class);
		
		//now instantiate an Inventory List to add Inventory objects to
		List<Inventory> inventoryList = new ArrayList<>();
		
		//now we need to data from sales- it would be the current sales data (todays sales)
		//we are taking today as 30th March (hard coding this but for the future- a transactions
		//service could be implemented which would interact with the sales data and update
		//the sales as the transactions go through) 
		LocalDate dateToday = LocalDate.of(2023, 3, 30);
		for(Product p:searchedProdcuts) {
			
			int prodId = p.getProductId();
			//get the sale object from sale dataset via prodId using REST API (as interacting with Inventory-Sale microservice)
			Sale sale = restTemplate.getForObject("http://localhost:8081/sales/"+prodId+"/"+dateToday, Sale.class);
			
			//set default sales amount to 0 then check if that sales record exists for the product in question
			Inventory inventoryItem = new Inventory(prodId, dateToday, p.getProductName(), p.getProductCategory(),
					0, p.getStock());
			
			if(sale != null)
				inventoryItem.setSalesAmount(sale.getSalesAmount());
			
			//add the inventoryItem to the list of current inventory items
			inventoryList.add(inventoryItem);
			
		}
		
		return inventoryList;
	}
	
	/*functionality to view the sales records over time.
	  Returns list of inventory records between the 2 dates specified */
	@Override 
	public List<Inventory> salesBetweenDates(LocalDate start, LocalDate end){
		
		//get a list array of the sales between desired dates, using GET request to fill the array with the sales records between dates specified 
		Sale[] sales = restTemplate.getForObject("http://localhost:8081/saless/"+start+"/"+end, Sale[].class);
		
		List<Inventory> inventoryList = new ArrayList<>();
		//for each sale, fetch the product properties and gather inventory object,
		//storing all these objects in the inventory list 
		for(Sale s: sales) {
			
			int id = s.getProductId();
			Product prod = restTemplate.getForObject("http://localhost:8080/products/"+id, Product.class);
			
			//if a product is deleted, it wont be found in products table, hence need to add default details 
			//to the inventory object here
			if(prod!=null) {
				Inventory inventory = new Inventory(id, s.getSaleDate(), prod.getProductName(), prod.getProductCategory(), s.getSalesAmount(), prod.getStock());
				inventoryList.add(inventory);
			}else {
				Inventory inventory = new Inventory(id, s.getSaleDate(), null, "Deleted", s.getSalesAmount(), 0);
				inventoryList.add(inventory);
			}
				
		}
		
		return inventoryList;
	}
	
	//GET request to get the product via the product Id (for the edit stock functionality in the controller)
	//Returns: product object or null if no such product with input product Id found in products table
	@Override
	public Product getProduct(int prodId) {
		Product prod = restTemplate.getForObject("http://localhost:8080/products/"+prodId, Product.class);
		return prod;
	}

	//aggregating sales data via product category to plot on a time series
	@Override
	public Map<String, Map<LocalDate, Integer>> aggreggatedData() {
		
		//the start and end dates in the sales database 
		LocalDate start = LocalDate.of(2023, 3, 1);
		LocalDate end = LocalDate.of(2023, 3, 30);
		List<Inventory> allInventory = salesBetweenDates(start, end);
		
		//using stream to group by the product categories and sales dates, and sum the daily sales
		//over each category for each day (for the 1 month that the business has been alive)
		Map<String, Map<LocalDate, Integer>> aggregatedMap = allInventory.stream()
                .collect(Collectors.groupingBy(Inventory::getProductCategory, 
                                Collectors.groupingBy(Inventory::getSaleDate, 
                                        Collectors.summingInt(inven -> inven.getSalesAmount()))));
		
		return aggregatedMap;
	}
	

}
