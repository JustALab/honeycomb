package com.honeycakesin.dto;

import com.honeycakesin.constants.AvailabilityStatus;
import com.honeycakesin.constants.ItemCategory;
import com.honeycakesin.entities.Item;
import com.honeycakesin.entities.VendorItems;

import lombok.Data;

@Data
public class VendorItemsDto {

	Long itemId;
	
	String itemName;
	
	ItemCategory itemCategory;
	
	Double itemPrice;
	
	Integer quantitySlab;
	
	AvailabilityStatus availabilityStatus;

	public VendorItemsDto(VendorItems vendorItems) {
		Item itemDto = vendorItems.getItem();
		this.availabilityStatus = vendorItems.getAvailabilityStatus();
		this.itemId = itemDto.getItemId();
		this.itemName = itemDto.getItemName();
		this.itemCategory = itemDto.getItemCategory();
		this.itemPrice = itemDto.getItemPrice();
		this.quantitySlab = itemDto.getQuantitySlab();
	}

}
