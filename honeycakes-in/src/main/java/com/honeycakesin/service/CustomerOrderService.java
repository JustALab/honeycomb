package com.honeycakesin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeycakesin.entities.Location;
import com.honeycakesin.entities.VendorItems;
import com.honeycakesin.repository.LocationRepository;
import com.honeycakesin.repository.VendorItemsRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author Ramu Ramasamy
 * @version 1.0
 * 
 */
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerOrderService {
	
	LocationRepository locationRepository;
	
	VendorItemsRepository vendorItemsRepository;

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return List of locations.
	 */
	public List<Location> getLocationList(){
		return locationRepository.findAllLocationsWithOnlyVendorId();
	}
	
	/**
	 * getVendorItemsList method returns a list of items available for the specified vendor ID.
	 * 
	 * @param vendorId
	 * @return List of items available with the vendor.
	 */
	public List<VendorItems> getVendorItemsList(Long vendorId){
		return vendorItemsRepository.findAllByVendorId(vendorId);
	}
		
}
