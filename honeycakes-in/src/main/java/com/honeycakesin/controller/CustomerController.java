package com.honeycakesin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.honeycakesin.auth.CustomJwtTokenUtil;
import com.honeycakesin.dto.CustomerDto;
import com.honeycakesin.dto.CustomerOrderDto;
import com.honeycakesin.dto.LocationDto;
import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.entities.Order;
import com.honeycakesin.service.CustomerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author Ramu Ramasamy
 * 
 */
@RestController
@RequestMapping("hc/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

	static final String AUTH_HEADER = "Authorization";

	CustomJwtTokenUtil tokenUtil;

	CustomerService customerOrderService;

	/**
	 * @param authorizationHeader
	 * @return Customer DTO that contains Customer information
	 */
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public CustomerDto getCustomer(@RequestHeader(value = AUTH_HEADER) String authorizationHeader) {
		return customerOrderService.getCustomer(tokenUtil.getUsernameFromToken(authorizationHeader));
	}

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return List of locations.
	 */
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public List<LocationDto> getLocationsList() {
		return customerOrderService.getLocationList();
	}

	/**
	 * getVendorItemsList method returns a list of items available for the specified
	 * vendor ID.
	 * 
	 * @param vendorId
	 * @return List of items available with the vendor.
	 */
	@RequestMapping(value = "/vendoritems/{vendorId}", method = RequestMethod.GET)
	public List<VendorItemsDto> getVendorItemsList(@PathVariable("vendorId") Long vendorId) {
		return customerOrderService.getVendorItemsList(vendorId);
	}

	/**
	 * placeOrder method places order based on the params received.
	 * 
	 * @param authorizationHeader
	 * @return Order
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public Order placeOrder(@RequestHeader(value = AUTH_HEADER) String authorizationHeader,
			@Valid @RequestBody CustomerOrderDto orderDto) {
		System.out.println(tokenUtil.getUsernameFromToken(authorizationHeader));
		return null;
	}

}
