package com.products.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Lombok annotations which remove the need for manually writing
  getters, setters and constructors for the object */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  //a spring JPA entity which allows for the creation of this object as a table in MySQL
public class Product {
	
	@Id
	private int productId; //primary key (in mySQL) signified by @Id annotation
	private String productName, productCategory;
	private int stock;
	
}
