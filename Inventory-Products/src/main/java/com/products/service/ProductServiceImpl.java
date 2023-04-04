package com.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.products.entity.Product;
import com.products.persistence.ProductDao;

import lombok.Setter;

@Service
public class ProductServiceImpl implements ProductService {

	//wiring the persistence layer (by creating an instance of it) in order to use the methods in Product Dao
	@Autowired
	private ProductDao productDao;
	
	//returns all the product objects into a list
	@Override
	public List<Product> getAllProducts() {
		return productDao.findAll();
	}
	
	//returns the product via the product Id and null if no such product with 
	//specified product Id exists in the SQL table 
	@Override
	public Product getProductByProdId(int productId) {
		return productDao.searchProductByProductId(productId);
	}

	//inserts product object into the product table (in MySQL), returning true if product is successfully inserted
	@Override
	public boolean addProduct(Product product) {
		
		//take the max product id +1 in the products table;
		//this will be prod Id of the first element- first use order by- in the array +1
		//this could of also been done with auto increment in MySQL
		List<Product> allProducts = productDao.findAll(Sort.by(Sort.Direction.DESC, "productId"));
		int lastProdId = allProducts.get(0).getProductId();
		product.setProductId(lastProdId + 1);
		
		//try catch block to catch any exceptions, returning false meaning product
		//was unsuccessful in its insertion 
		try {
			
			productDao.insertProduct(product.getProductId(), product.getProductName(), 
					product.getProductCategory(), product.getStock());
			
			return true;
			
		} catch(Exception e){
			return false;
		}
		
	}

	/*reordering items-updating the product table by adding 'amount'
	  to the current amount of stock, returning true if stock has been updated*/
	@Override
	public boolean reorderItems(int prodId, int amount) {
		return (productDao.updateStock(amount, prodId)>0);
	}

	//deleting  item via product Id, returning true if item successfully deleted
	@Override
	public boolean deleteItem(int prodId) {
		return (productDao.deleteProduct(prodId)>0);
	}

	/*search for items in product category or product name fields.
	  Returns non-empty list with products matching search description or null 
	  if no such product name/category exists in the MySQL table */
	@Override
	public List<Product> searchItems(String input) {
		 return productDao.searchItems(input);
	}
	
	
	
	

}
