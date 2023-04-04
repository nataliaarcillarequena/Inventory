package com.sales.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.entity.Sale;
import com.sales.persistence.SaleDao;

@Service
public class SaleServiceImpl implements SaleService {

	//wiring the persistence layer (Data access object- DAO) in order to access all of the methods from this interface
	@Autowired
	private SaleDao saleDao;
	
	/* this class includes the implementation of all the functionality
	 * which the sales service has to offer; inserting sales,
	 * retrieving sales records over desired time frames or retrieving
	 * specific sales records (unique to a certain day and product Id)
	 */
	
	/*inserting a sales record into mySql table: sales (initial insert to 
	 * get the dataset up- not used in functionality of this web app) as 
	 * described in the SaleController class
	 */
	@Override
	public boolean insertSales(Sale sale) {
		try {
			saleDao.insertSalesRecord(sale.getProductId(), sale.getSaleDate(), 
					sale.getSalesAmount());
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	/*returns a list of sales objects in the date range specified,
	  null is returned if no records exist in date range specified */
	@Override
	public List<Sale> saleRecordsInDateRange(LocalDate start, LocalDate end) {
		return saleDao.saleInDateRange(start, end);
	}

	/*returns list of sales objects for a specific day- 
	  or null if no sales records for that day in the dataset */
	@Override
	public List<Sale> saleRecordsBySaleDate(LocalDate date) {
		return saleDao.findBySaleDate(date);
	}

	/*returns the Sale object with specified id (composite id of 
	  productId and saleDate) otherwise null if no such sale record
	  with specified id exists in the dataset */
	@Override
	public Sale saleRecordById(int prodId, LocalDate saleDate) {
		return saleDao.findByProductIdAndSaleDate(prodId, saleDate);
	}

}
