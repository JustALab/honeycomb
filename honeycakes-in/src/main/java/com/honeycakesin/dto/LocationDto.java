package com.honeycakesin.dto;

import com.honeycakesin.entities.Location;

import lombok.Data;

@Data
public class LocationDto {

	Long locationId;

	String locationName;

	Long deliveryVendorId;
	
	public LocationDto(Location locationDto) {
		this.locationId = locationDto.getLocationId();
		this.locationName = locationDto.getLocationName();
		this.deliveryVendorId = locationDto.getDeliveryVendor().getVendorId();
	}

	public Long getDeliveryVendorId() {
		return this.deliveryVendorId;
	}

}
