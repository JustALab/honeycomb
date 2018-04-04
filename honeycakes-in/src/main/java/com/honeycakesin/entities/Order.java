package com.honeycakesin.entities;

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
import com.honeycakesin.constants.DeliveryToAddressType;
import com.honeycakesin.constants.FeedbackStatus;
import com.honeycakesin.constants.OrderStatus;
import com.honeycakesin.constants.PaymentMode;

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
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long orderNumber;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	Customer customer;

	@ManyToOne
	@JoinColumn(name = "vendor_id")
	Vendor vendor;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	Date orderDateTime;

	@Column(nullable = false)
	String deliveryDate;

	@Column(nullable = false)
	String deliveryTime;

	@Column(nullable = false)
	Double totalAmount;
	
	@Enumerated(EnumType.STRING)
	PaymentMode paymentMode;
	
	@Enumerated(EnumType.STRING)
	DeliveryToAddressType deliveryToAddressType;

	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	FeedbackStatus feedbackStatus;
	
	@OneToMany(mappedBy = "order")
	Set<OrderItems> orderItemsSet;
	
	@OneToOne(mappedBy = "order")
	OrderFeedback orderFeedback;
}
