package com.inventory.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.inventory.entity.Inventory;
import com.inventory.entity.Product;
import com.inventory.service.InventoryService;

//Spring Boot annotation to notify Spring that this is the controller class for autoconfiguration 
@Controller
public class InventoryController {
	
	
	/*
	 * This Controller class wires together the functionality of
	 * the app (from the inventory service layer) and controls the 
	 * screens that the user sees when interacting with the web app. 
	 * Request mappings are used to tell the controller which functionality
	 * the user is trying to access and responds by performing the appropriate 
	 * function and adding the output to the model and view. The Views are the
	 * html files in the resources/templates folder and determine what the 
	 * user sees on the screen. This controller sets the appropriate
	 * view based on what functionality the user is trying to access on the 
	 * web page, with the requested information, by adding this to the Model and view.
	 */
	
	//instance of the Inventory service created to allow for use of the InventoryService methods in this contoller class
	@Autowired
	private InventoryService inventoryService;
	
	//the first page shown is the current inventory
	@RequestMapping("/")
	public ModelAndView inventoryPage() {
		
		//new page controller
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("CurrentInventory");
		//assuming today is 30th march 2023
		LocalDate today = LocalDate.of(2023, 3, 30);
		List<Inventory> currentInventory = inventoryService.getCurrentInventory(today);
		
		//adding the current inventory to the view for the user to see
		modelAndView.addObject("allInventory", currentInventory);
		
		return modelAndView;
		
	}
	
	/*restock clicked on table, will take us to the restock page,
	  asking for how many items to restock
	  Requesting the product Id that the user has requested to restock*/
	@RequestMapping("/restockPage")
	public ModelAndView restockPageController(@RequestParam("productId")int pid) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//returning the product via the product id 
		Product product = inventoryService.getProduct(pid);
		
		//adding the product to the model, ready for a restock quantity request to be input by the user
		modelAndView.addObject("product", product);
		modelAndView.setViewName("Restock");
		
		return modelAndView;	
	}
	
	/*the restock functionality which increases/decreases the stock of the product that 
	  the user has requested to restock, with the quantity requested*/
	@RequestMapping("/restock")
	public ModelAndView restockFunctionality(@ModelAttribute("product")Product product) {
		
		ModelAndView modelAndView = new ModelAndView();
		//quantity of stock to add/remove that user requested was stored in the stock var of the product object
		int updateQuantity = product.getStock();
		
		//product table is updated with the new stock if valid product Id has been provided
		//then a sucess of update message is shown on the web page 
		//otherwise unsuccessful product update message is printed on the screen
		inventoryService.reorderProduct(product.getProductId(), updateQuantity);
		Product existingProduct = inventoryService.getProduct(product.getProductId());
		if(existingProduct!=null) {
			modelAndView.addObject("message", "Product stock edit successful!"); 
		}else {
			modelAndView.addObject("message", "Product stock edit unsuccessful, please try again.");
		}
			
		//the current inventory page is shown also with the above message 
		LocalDate today = LocalDate.of(2023, 3, 30);
		List<Inventory> currentInventory = inventoryService.getCurrentInventory(today);
		modelAndView.addObject("allInventory", currentInventory);
		modelAndView.setViewName("CurrentInventory");
		
		return modelAndView;
	}
	
	
	//deletes a product and shows message of product successfully deleted
	@RequestMapping("/delete")
	public ModelAndView deleteProduct(@RequestParam("productId")int pid) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//deletes the product
		inventoryService.deleteProduct(pid);
		
		//shows the current inventory page, with the current inventory
		LocalDate today = LocalDate.of(2023, 3, 30);
		List<Inventory> currentInventory = inventoryService.getCurrentInventory(today);
		modelAndView.addObject("allInventory", currentInventory);
		modelAndView.addObject("message", "Product successfully deleted!");  
		
		modelAndView.setViewName("CurrentInventory");
		return modelAndView;
	}
	
	//navigates to the Add Product page
	@RequestMapping("/addProductPage")
	public ModelAndView addProductPage() {
		return new ModelAndView("Addproduct");
	}
	
	//submits add product request 
	@RequestMapping("/addProduct")
	public ModelAndView addProductFunctionality(@RequestParam("prodName")String pName, 
				@RequestParam("prodCat")String pCat, @RequestParam("stock")int stock) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//creating new product with the values that the user has entered and adding to the 
		//products table, a message based on the success of the insert is the printed on the screen
		Product product = new Product(0, pName, pCat, stock);
		if(inventoryService.addProduct(product)) {
			modelAndView.addObject("message", "Product successfully added!");
		}else {
			modelAndView.addObject("message", "Product unsuccessfully added, please try again.");
		}
		
		//setting the inventory view after submitting the add product request to show the main page;
		//the current inventory page with the current inventory table
		LocalDate today = LocalDate.of(2023, 3, 30);
		List<Inventory> currentInventory = inventoryService.getCurrentInventory(today);
		modelAndView.addObject("allInventory", currentInventory);
		modelAndView.setViewName("CurrentInventory");
		
		return modelAndView;
	}
	
	//search the inventory functionality
	@RequestMapping("/inventorySearch")
	public ModelAndView inventorySearch(@RequestParam("input")String input) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//search the inventory via users input, putting the objects into a list
		List<Inventory> searchedInventory = inventoryService.searchInventory(input);
		
		//add the inventory found through the users search to the model and view
		//to show the reults on the web page 
		modelAndView.addObject("allInventory", searchedInventory);
		
		modelAndView.setViewName("CurrentInventory");
		
		return modelAndView;
	}
	
	//get the sales over the whole month (for as long as Aperture has existed)
	@RequestMapping("/allSales")
	public ModelAndView viewAllSales() {
		ModelAndView modelAndView = new ModelAndView();
		
		//the start and end dates in the sales database 
		LocalDate start = LocalDate.of(2023, 3, 1);
		LocalDate end = LocalDate.of(2023, 3, 30);
		List<Inventory> salesReport = inventoryService.salesBetweenDates(start, end);
		
		//add the list of sales for the month to the view page 
		modelAndView.addObject("allInventory", salesReport);
		modelAndView.addObject("message", "All Sales");
		modelAndView.setViewName("SalesReports");
		
		return modelAndView;
	}
	
	/*get the sales over the last week (from 30th march minus 7 days)- 
	  all 30th's of march 2023 would be set to LocalDate.today() in real world
	  when sales DB is being updated everyday, however the last time data
	  was input was 30th March*/
	@RequestMapping("/weekSales")
	public ModelAndView viewWeekSales() {
		
		ModelAndView modelAndView = new ModelAndView();
		
		LocalDate end = LocalDate.of(2023, 3, 30);
		LocalDate start = end.minusDays(7);
		List<Inventory> salesReport = inventoryService.salesBetweenDates(start, end);
		
		//returns a page with Aperture's daily sales over the page week
		modelAndView.addObject("allInventory", salesReport);
		modelAndView.addObject("message", "Sales over the last week");
		modelAndView.setViewName("SalesReports");
		
		return modelAndView;
	}
	
	
	
	
}
