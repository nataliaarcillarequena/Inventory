package com.sales.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(CompositeId.class)
public class Sale {
	
	/*A composite id used for the sales table-
	  see the compositeId class for more information */
	@Id
	private int productId;
	@Id
	private LocalDate saleDate;
	private int salesAmount;
	
}
