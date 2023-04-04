package com.products.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.products.entity.Product;

/*this Dao interface extends the JpaRepository which allows 
 * for the querying of the SQL datasets*/
@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
 
	
	//returns the product object via product id 
	public Product searchProductByProductId(int productId);
	
	//insert product into the database- via native query - returns 1 when product successfully inserted into SQL table
	@Modifying
	@Transactional
	@Query(value = "insert into product values(:id, :pname, :cname, :stock)", nativeQuery = true)
	public int insertProduct(@Param("id") int productId, @Param("pname") String productName, @Param("cname") String productCategory, 
			@Param("stock")int stock);
	
	/*update the stock of a product (when reordering items- instantaneous update of stock)
	  will return int greater than 0 when successfully updated */
	@Modifying
	@Transactional
	@Query("update Product set stock = stock + :inc where productId = :id")
	public int updateStock(@Param("inc") int reorderAmount, @Param("id")int prodId);
	
	//deleting a product from products table, will return an integer > 0 when product successfully deleted 
	@Modifying
	@Transactional
	@Query("delete from Product where productId = :id")
	public int deleteProduct(@Param("id")int prodId);
	
	/*search table for specific category/product name, returning a list of the products 
	  with name/category containing the users input word. If no such product exists then 
	  null is returned */
	@Query("select p from Product p where p.productName like %:search% or p.productCategory like %:search%")
	public List<Product> searchItems(@Param("search") String input);
	
}
