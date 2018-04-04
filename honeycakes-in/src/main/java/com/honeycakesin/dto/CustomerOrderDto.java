package com.honeycakesin.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.honeycakesin.constants.DeliveryToAddressType;
import com.honeycakesin.constants.PaymentMode;
import com.honeycakesin.entities.Order;
import com.honeycakesin.entities.OrderItems;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class CustomerOrderDto implements Serializable {

	@NotNull
	Long vendorId;

	@NotNull
	String deliveryDate;

	@NotNull
	String deliveryTime;

	@NotNull
	Double totalAmount;

	@NotNull
	PaymentMode paymentMode;

	@NotNull
	DeliveryToAddressType deliveryToAddressType;

	@NotNull
	List<OrderItems> orderItemsList;

	public CustomerOrderDto(Order order) {
		this.vendorId = order.getVendor().getVendorId();
		this.deliveryDate = order.getDeliveryDate();
		this.deliveryTime = order.getDeliveryTime();
		this.totalAmount = order.getTotalAmount();
		this.paymentMode = order.getPaymentMode();
		this.deliveryToAddressType = order.getDeliveryToAddressType();
		this.orderItemsList = order.getOrderItemsList();
	}

}
