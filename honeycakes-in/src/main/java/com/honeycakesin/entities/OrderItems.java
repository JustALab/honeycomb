package com.honeycakesin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "order_items")
public class OrderItems implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long orderItemsNumber;
	
	@ManyToOne
	@JoinColumn(name = "order_number")
	Order order;
	
	@OneToOne
	@JoinColumn(name = "item_id")
	Item item;
	
	@Column(nullable = false)
	Integer quantity;
	
	@Column(nullable = false)
	Double price;
	
}
