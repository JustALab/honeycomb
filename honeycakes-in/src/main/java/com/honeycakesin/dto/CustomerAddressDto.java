package com.honeycakesin.dto;

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

import com.honeycakesin.utils.DeliveryToAddressType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "customer_addresses")
public class CustomerAddressDto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long addressId;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	CustomerDto customerDto;

	@Enumerated(EnumType.STRING)
	DeliveryToAddressType deliveryToAddressType;
	
	@Column(nullable = true)
	String address;
}
