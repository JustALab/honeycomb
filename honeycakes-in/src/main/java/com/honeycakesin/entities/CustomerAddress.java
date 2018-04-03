package com.honeycakesin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.honeycakesin.constants.DeliveryToAddressType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "customer_addresses")
public class CustomerAddress implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long addressId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Customer customer;

	@Enumerated(EnumType.STRING)
	DeliveryToAddressType deliveryToAddressType;
	
	@Column(nullable = true)
	String address;
}
