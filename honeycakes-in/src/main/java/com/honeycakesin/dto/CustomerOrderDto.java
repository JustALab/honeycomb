package com.honeycakesin.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.honeycakesin.constants.DeliveryAddressType;
import com.honeycakesin.constants.PaymentMode;
import com.honeycakesin.entities.Order;

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
	DeliveryAddressType deliveryAddressType;
	
	@NotNull
	String deliveryAddress;

	@NotNull
	List<CustomerOrderItemsDto> orderItemsList;

	public CustomerOrderDto(Order order) {
		this.vendorId = order.getVendor().getVendorId();
		this.deliveryDate = order.getDeliveryDate();
		this.deliveryTime = order.getDeliveryTime();
		this.totalAmount = order.getTotalAmount();
		this.paymentMode = order.getPaymentMode();
		this.deliveryAddressType = order.getDeliveryAddressType();
		this.deliveryAddress = order.getDeliveryAddress();
	}

}
