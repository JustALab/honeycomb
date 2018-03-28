package com.honeycakesin.dto;

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
public class OrderItemsDto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long orderItemsNumber;
	
	@ManyToOne
	@JoinColumn(name = "order_number")
	OrderDto orderDto;
	
	@OneToOne
	@JoinColumn(name = "item_id")
	ItemDto itemDto;
	
	@Column(nullable = false)
	int quantity;
	
	@Column(nullable = false)
	double price;
	
}