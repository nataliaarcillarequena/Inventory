package com.sales.persistence;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sales.entity.CompositeId;
import com.sales.entity.Sale;

//Bean informing spring that this is the persistence interface 
@Repository
public interface SaleDao extends JpaRepository<Sale, CompositeId> {

	//insert sale record into the database- via native query - returns 1 when sale successfully inserted into sql table
	@Modifying
	@Transactional		
	@Query(value = "insert into sale values(:id, :sdate, :samount)", nativeQuery = true)
	public int insertSalesRecord(@Param("id") int productId, @Param("sdate") LocalDate saleDate, @Param("samount") int saleAmount);
	
	//returns the sales records between 2 dates (inclusive)
	@Query("select s from Sale s where s.saleDate between :d1 and :d2")
	public List<Sale> saleInDateRange(@Param("d1")LocalDate start, @Param("d2")LocalDate end);
	
	//returns the sales records for a specific day
	public List<Sale> findBySaleDate(LocalDate date);
		
	//returns the sales records by the Id (Id is a composite Id made up of productId and sale date)
	public Sale findByProductIdAndSaleDate(int prodId, LocalDate saleDate);
	
}
