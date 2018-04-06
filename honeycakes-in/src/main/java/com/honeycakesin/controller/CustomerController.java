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
import com.honeycakesin.dto.OrderFeedbackDto;
import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.Order;
import com.honeycakesin.entities.OrderFeedback;
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
	 * placeOrder method places order based on the CustomerOrderDto and Customer
	 * received.
	 * 
	 * @param authorizationHeader
	 * @return Order
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public Order placeOrder(@RequestHeader(value = AUTH_HEADER) String authorizationHeader,
			@Valid @RequestBody CustomerOrderDto customerOrderDto) {
		Customer customer = tokenUtil.getCustomer(authorizationHeader);
		return customerOrderService.placeOrder(customer, customerOrderDto);
	}

	/**
	 * getOrderHistory method gets the list of orders made by the customer.
	 * 
	 * @param authorizationHeader
	 * @return customerOrderDtoList
	 */
	@RequestMapping(value = "/orderhistory", method = RequestMethod.GET)
	public List<CustomerOrderDto> getOrderHistory(@RequestHeader(value = AUTH_HEADER) String authorizationHeader) {
		Customer customer = tokenUtil.getCustomer(authorizationHeader);
		return customerOrderService.getOrderHistory(customer);
	}

	/**
	 * submitOrderFeedback method is used to submit order feedback for the specified
	 * orderNumber.
	 * 
	 * @param orderNumber
	 * @param orderFeedbackDto
	 * @return OrderFeedback
	 */
	@RequestMapping(value = "/orderfeedback/{orderNumber}", method = RequestMethod.POST)
	public OrderFeedback submitOrderFeedback(@PathVariable("orderNumber") Long orderNumber,
			@Valid @RequestBody OrderFeedbackDto orderFeedbackDto) {
		return customerOrderService.submitOrderFeedback(orderNumber, orderFeedbackDto);
	}

}
