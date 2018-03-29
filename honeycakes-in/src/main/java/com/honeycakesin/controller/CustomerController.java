package com.honeycakesin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honeycakesin.dto.LocationDto;
import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.service.CustomerOrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author Ramu Ramasamy
 * @version 1.0
 * 
 */
@RestController
@RequestMapping("hc/customer")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

	CustomerOrderService customerOrderService;

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return List of locations.
	 */
	@GetMapping("/locations")
	public List<LocationDto> getLocationsList() {
		return customerOrderService.getLocationList();
	}

	/**
	 * getVendorItemsList method returns a list of items available for the specified vendor ID.
	 * 
	 * @param vendorId
	 * @return List of items available with the vendor.
	 */
	@GetMapping("/vendoritems/{vendorId}")
	public List<VendorItemsDto> getVendorItemsList(@PathVariable("vendorId") Long vendorId) {
		return customerOrderService.getVendorItemsList(vendorId);
	}

}
