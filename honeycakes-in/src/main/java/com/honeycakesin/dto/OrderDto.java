package com.honeycakesin.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honeycakesin.utils.DeliveryToAddressType;
import com.honeycakesin.utils.FeedbackStatus;
import com.honeycakesin.utils.OrderStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "orderDateTime" }, allowGetters = true)
@Table(name = "orders")
public class OrderDto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long orderNumber;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	CustomerDto customerDto;

	@ManyToOne
	@JoinColumn(name = "vendor_id")
	VendorDto vendorDto;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	Date orderDateTime;

	@Temporal(TemporalType.DATE)
	Date deliveryDate;

	@Temporal(TemporalType.TIME)
	Date deliveryTime;

	@Column(nullable = false)
	double totalAmount;
	
	@Enumerated(EnumType.STRING)
	DeliveryToAddressType deliveryToAddressType;

	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	FeedbackStatus feedbackStatus;
	
	@OneToMany(mappedBy = "orderDto")
	Set<OrderItemsDto> orderItemsDtoSet;
	
	@OneToOne(mappedBy = "orderDto")
	OrderFeedbackDto orderFeedbackDto;
}
