package com.sales.resource;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sales.entity.Sale;
import com.sales.service.SaleService;

@RestController
public class SaleResource {

	//wiring the service layer, allowing for use of the SaleService methods 
	@Autowired
	private SaleService saleService;
	
	/* 
	 * These methods expose the REST API, using GET request 
	 * in order to transfer information between this sales microservice
	 * and the MVC. 
	 * Testing: the path of these requests is passed into Postman is used to test 
	 * whether these requests are responding as expected. 
	 */
	
	/*returning the list of sales records from the Sale database in a desired time frame-
	parsing needed when passing in a LocalDate, otherwise postman recognises this
	date as a string. GET request is used */
	@GetMapping(path="saless/{start}/{end}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Sale> salesResource(@PathVariable("start")
									@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, 
									@PathVariable("end")
									@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
		return saleService.saleRecordsInDateRange(start, end);
	}
	
	// GET request used to return the sales records for a specific date 
	@GetMapping(path="sales/{date}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Sale> salesDateResource(@PathVariable("date")
										@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
										LocalDate date){
		return saleService.saleRecordsBySaleDate(date);
	}
	
	//GET request used to return the sales records for a specific id; a composite id made of sale date and product id 
	@GetMapping(path="sales/{prodId}/{date}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Sale salesIdResource(@PathVariable("prodId") int pid, 
									@PathVariable("date")
									@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
									LocalDate date){
		return saleService.saleRecordById(pid, date);
	}
	
	
}
