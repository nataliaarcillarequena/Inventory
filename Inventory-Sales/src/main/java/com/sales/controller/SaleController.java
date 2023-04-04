package com.sales.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sales.entity.Sale;
import com.sales.service.SaleService;

@Controller
public class SaleController {

	@Autowired
	private SaleService saleService;
	
	/**
	 * This code was used to add the sales records into the sales dataset.
	 * The sales records where generated for the month (30 days) that Aperture
	 * has been in business and it includes the fields: product Id, sale date and
	 * number of sales. It has records for the 30 days from 1st march to 
	 * 30th march 2023, with random numbers generated for the daily sales; 
	 * each day having sales for all of the products 
	 * (Aperture is doing well in sales; their products have taken interest). 
	 * Instead of having the products and sales in 1 dataset, 
	 * this sales service was made following a microservice architecture
	 * to allow for future change requests such as adding a transactions service.
	 * This would not be favourable if a monolithic approach was taken.
	 * In addition, the splitting up of the many sales (450 records)
	 * and products records into separate tables improves speed of application 
	 * when not viewing all of the records (e.g. only current inventory).
	 *  */
	
	
	//the below code was written to insert all the sales records into the 
	//sql table in the mysqldb docker container 
//	@RequestMapping("/")
//	public ModelAndView homePage() {
//		
//		//finding all dates between 2 local dates 
//		LocalDate start = LocalDate.now().minusDays(30);			
//		LocalDate end = LocalDate.now();
//		//System.out.println(start);
//				
//		//numb of days between start and end- works
//		long days = ChronoUnit.DAYS.between(start, end);
//		//System.out.println(days);
//				
//		//get all the dates between start and end in a list (using stream)
//		List<LocalDate> allDates = Stream.iterate(start, date -> date.plusDays(1))
//				.limit(days).collect(Collectors.toList());
//				
//		//System.out.println(allDates);
//			
//		//make another list-list of sales records
//		List<Sale> salesRecords = new ArrayList<>();
//		//Sale sale = new Sale();
//		//random number generator (between 1 and 200 for the sales amounts)
//		Random rand = new Random();
//		int lowBound = 1;
//		int highBound = 200;
//				
//		//outter for loop- looping through all the dates
//		for(int dt=0; dt<allDates.size(); dt++) {
//			//inner for loop- for each date this will happen:
//			for(int id=101; id<116; id++) {
//				Sale sale = new Sale();
//					
//				//index 0 will be a variable- given by outer for loop
//				LocalDate recordDate = allDates.get(dt);
//				sale.setProductId(id);
//				sale.setSaleDate(recordDate);
//				int salesAmount = rand.nextInt(highBound-lowBound)+lowBound;
//				sale.setSalesAmount(salesAmount);
//					
//				salesRecords.add(sale);
//				saleService.insertSales(sale);
//			}
//		}
//				
//		//prints all the slaes records- done right
//		//salesRecords.forEach(System.out::println);
//		//supposed to be 450 records- yes there is 
//		//System.out.println(salesRecords.size());
//		
//		return new ModelAndView("Home");
//	}
	
}
