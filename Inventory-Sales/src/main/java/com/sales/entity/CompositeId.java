package com.sales.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class CompositeId implements Serializable {

	/*a composite id is employed for the sales table because 
	  a unique record is made up of the sale date and the
	  products id (for 1 product id there are many sale dates*/
	
	private int productId;
	private LocalDate saleDate;
	
}
