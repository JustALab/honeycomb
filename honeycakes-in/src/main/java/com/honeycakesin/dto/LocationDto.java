package com.honeycakesin.dto;

import com.honeycakesin.entities.Location;

import lombok.Data;

@Data
public class LocationDto {

	Long locationId;

	String locationName;

	Long deliveryVendorId;
	
	public LocationDto(Location location) {
		this.locationId = location.getLocationId();
		this.locationName = location.getLocationName();
		this.deliveryVendorId = location.getDeliveryVendor().getVendorId();
	}

	public Long getDeliveryVendorId() {
		return this.deliveryVendorId;
	}

}
