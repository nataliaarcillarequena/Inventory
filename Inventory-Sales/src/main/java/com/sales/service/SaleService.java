package com.sales.service;

import java.time.LocalDate;
import java.util.List;

import com.sales.entity.Sale;

public interface SaleService {

	/*inserting records into the sales table (done as a one-off in the initial set up 
	of this project; more information is given in the com.sales.controller package*/
	public boolean insertSales(Sale sale);
	
	//returns a list of the sales records between 2 chosen dates (inclusive)
	public List<Sale> saleRecordsInDateRange(LocalDate start, LocalDate end);
	
	//returns a list of the sales records for a specific day
	public List<Sale> saleRecordsBySaleDate(LocalDate date);
	
	//returns the Sale records by the Id (Id is a composite Id made up of product Id and sale date)
	public Sale saleRecordById(int prodId, LocalDate saleDate);
	
}
