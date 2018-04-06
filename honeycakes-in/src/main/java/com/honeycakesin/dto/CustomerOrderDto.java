package com.honeycakesin.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.honeycakesin.constants.DeliveryAddressType;
import com.honeycakesin.constants.FeedbackStatus;
import com.honeycakesin.constants.OrderStatus;
import com.honeycakesin.constants.PaymentMode;
import com.honeycakesin.entities.Order;
import com.honeycakesin.entities.OrderFeedback;
import com.honeycakesin.entities.OrderItems;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class CustomerOrderDto implements Serializable {

	/** orderNumber will be populated only for response. */
	@Null
	Long orderNumber;

	/** vendorName will be populated only for response. */
	@Null
	String vendorName;

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

	@Null
	OrderStatus orderStatus;

	/** feedbackStatus will be populated only for response. */
	@Null
	FeedbackStatus feedbackStatus;
	
	/** feedbackStatus will be populated only for response. */
	@Null
	OrderFeedback orderFeedback;

	/** orderDateTime will be populated only for response. */
	@Null
	Date orderDateTime;

	/** orderItemsHistoryList will be populated only for response. */
	@Null
	List<OrderItems> orderItemsHistoryList;

	public CustomerOrderDto(Order order) {
		this.orderNumber = order.getOrderNumber();
		this.vendorId = order.getVendor().getVendorId();
		this.vendorName = order.getVendor().getVendorName();
		this.deliveryDate = order.getDeliveryDate();
		this.deliveryTime = order.getDeliveryTime();
		this.totalAmount = order.getTotalAmount();
		this.paymentMode = order.getPaymentMode();
		this.deliveryAddressType = order.getDeliveryAddressType();
		this.deliveryAddress = order.getDeliveryAddress();
		this.orderDateTime = order.getOrderDateTime();
		this.orderItemsHistoryList = order.getOrderItemsList();
		this.orderStatus = order.getOrderStatus();
		this.feedbackStatus = order.getFeedbackStatus();
		this.orderFeedback = order.getOrderFeedback();
	}

}
