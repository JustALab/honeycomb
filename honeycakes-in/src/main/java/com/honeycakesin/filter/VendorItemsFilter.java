package com.honeycakesin.filter;

import com.honeycakesin.dto.ItemDto;
import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.utils.AvailabilityStatus;
import com.honeycakesin.utils.ItemCategory;

import lombok.Data;

@Data
public class VendorItemsFilter {

	long itemId;
	
	String itemName;
	
	ItemCategory itemCategory;
	
	double itemPrice;
	
	int quantitySlab;
	
	AvailabilityStatus availabilityStatus;

	public VendorItemsFilter(VendorItemsDto vendorItemsDto) {
		ItemDto itemDto = vendorItemsDto.getItemDto();
		this.availabilityStatus = vendorItemsDto.getAvailabilityStatus();
		this.itemId = itemDto.getItemId();
		this.itemName = itemDto.getItemName();
		this.itemCategory = itemDto.getItemCategory();
		this.itemPrice = itemDto.getItemPrice();
		this.quantitySlab = itemDto.getQuantitySlab();
	}

}
