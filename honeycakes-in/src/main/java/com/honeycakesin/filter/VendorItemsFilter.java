package com.honeycakesin.filter;

import com.honeycakesin.constants.AvailabilityStatus;
import com.honeycakesin.constants.ItemCategory;
import com.honeycakesin.dto.ItemDto;
import com.honeycakesin.dto.VendorItemsDto;

import lombok.Data;

@Data
public class VendorItemsFilter {

	Long itemId;
	
	String itemName;
	
	ItemCategory itemCategory;
	
	Double itemPrice;
	
	Integer quantitySlab;
	
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
