package com.honeycakesin.filter;

import com.honeycakesin.dto.LocationDto;

import lombok.Data;

@Data
public class LocationFilter {

	long locationId;

	String locationName;

	long deliveryVendorId;
	
	public LocationFilter(LocationDto locationDto) {
		this.locationId = locationDto.getLocationId();
		this.locationName = locationDto.getLocationName();
		this.deliveryVendorId = locationDto.getDeliveryVendor().getVendorId();
	}

	public Long getDeliveryVendorId() {
		return this.deliveryVendorId;
	}

}
