package com.honeycakesin.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class CustomerOrderItemsDto implements Serializable {
	
	@NotNull
	Long itemId;
	
	@NotNull
	Double quantity;
	
	@NotNull
	Double price;

}
