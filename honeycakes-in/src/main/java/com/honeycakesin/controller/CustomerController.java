package com.honeycakesin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.honeycakesin.entities.Location;
import com.honeycakesin.entities.VendorItems;
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
@PreAuthorize("hasRole('CUSTOMER')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

	CustomerOrderService customerOrderService;

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return List of locations.
	 */
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public List<Location> getLocationsList() {
		return customerOrderService.getLocationList();
	}

	/**
	 * getVendorItemsList method returns a list of items available for the specified vendor ID.
	 * 
	 * @param vendorId
	 * @return List of items available with the vendor.
	 */
	@RequestMapping(value = "/vendoritems/{vendorId}", method = RequestMethod.GET)
	public List<VendorItems> getVendorItemsList(@PathVariable("vendorId") Long vendorId) {
		return customerOrderService.getVendorItemsList(vendorId);
	}

}
