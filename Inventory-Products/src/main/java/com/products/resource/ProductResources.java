package com.products.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.products.entity.Product;
import com.products.service.ProductService;
import com.products.service.ProductServiceImpl;

@RestController
public class ProductResources {

	//wiring the service layer, allowing for use of the ProductService methods by creating an instance of this interface
	@Autowired
	private ProductService productService;
	
	/* 
	 * These methods expose the REST API, using GET, PUT DELETE and POST 
	 * requests in order to transfer information between this products microservice
	 * and the MVC. 
	 * Testing: the path of these requests is passed into Postman is used to test 
	 * whether these requests are responding as expected. 
	 */
	
	//using the GET method to return a list of all the products in the SQL product table 
	@GetMapping(path="products/all", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Product> AllProductsResource(){
		return productService.getAllProducts();
	}
	
	//using GET request to retrieve a product by product Id
	@GetMapping(path="products/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Product productViaIdResource(@PathVariable("id")int prodId){
		return productService.getProductByProdId(prodId);
	}
	
	/*using the POST request to insert a product record into the SQL product table.
	  Returning a string informing the user whether the product has been successfully added */
	@RequestMapping(path="products", method = RequestMethod.POST, 
			produces= MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addProdResource(@RequestBody Product prod) {
		if(productService.addProduct(prod))
			return "product added";
		return "product not added";			
	}
	
	 
	
	/*using the PUT request to update the stock of a product in the SQL product table
	  returning a string informing the user whether the product has been successfully updated in the table*/
	@RequestMapping(path="products/{id}/{inc}", method=RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateStock(@PathVariable("id") int prodId, @PathVariable("inc") int amount) {
		if(productService.reorderItems(prodId, amount))
			return "stock updated";
		return "stock not updated";
	}
	
	/*using the DELETE request to delete a product from the SQL product table
	  returning a string informing the user whether the product has been successfully deleted */
	@RequestMapping(path="products/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String deleteProduct(@PathVariable("id")int prodId) {
		if(productService.deleteItem(prodId))
			return "item successfully deleted";
		return "item unsuccessfully deleted";
	}
	
	//using a GET request to return a list of the product records based on the users searching criteria, the 'input' String
	@GetMapping(path="products/search/{input}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Product> searchForProductResource(@PathVariable("input")String input){
		return productService.searchItems(input);
	}
	
	
	
	
}
