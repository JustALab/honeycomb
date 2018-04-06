package com.honeycakesin.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.honeycakesin.constants.OrderRating;
import com.honeycakesin.entities.OrderFeedback;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class OrderFeedbackDto implements Serializable {

	@Null
	Long orderNumber;

	@NotNull
	OrderRating orderRating;

	@NotNull
	String comments;

	@Null
	Date feedbackDateTime;

	public OrderFeedbackDto(OrderFeedback orderFeedback) {
		this.orderNumber = orderFeedback.getOrder().getOrderNumber();
		this.orderRating = orderFeedback.getOrderRating();
		this.comments = orderFeedback.getComments();
		this.feedbackDateTime = orderFeedback.getFeedbackDateTime();
	}

}
