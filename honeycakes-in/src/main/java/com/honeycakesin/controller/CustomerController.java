package com.honeycakesin.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	CustomerService customerService;

	/**
	 * @param authorizationHeader
	 * @return Customer DTO that contains Customer information
	 */
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public CustomerDto getCustomer(@RequestHeader(value = AUTH_HEADER) String authorizationHeader) {
		return customerService.getCustomer(tokenUtil.getUsernameFromToken(authorizationHeader));
	}

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return List of locations.
	 */
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public List<LocationDto> getLocationsList() {
		return customerService.getLocationList();
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
		return customerService.getVendorItemsList(vendorId);
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
		return customerService.placeOrder(customer, customerOrderDto);
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
		return customerService.getOrderHistory(customer);
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
	public ResponseEntity<?> submitOrderFeedback(@PathVariable("orderNumber") Long orderNumber,
			@Valid @RequestBody OrderFeedbackDto orderFeedbackDto) {
		return ResponseEntity.ok(customerService.submitOrderFeedback(orderNumber, orderFeedbackDto));
	}

	/**
	 * deleteAddress method deletes the address with the given addressId.
	 * 
	 * @param addressId
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/deleteAddress/{addressId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomerAddress(@PathVariable("addressId") Long addressId) {
		customerService.deleteAddress(addressId);
		return ResponseEntity.ok().build();
	}

	/**
	 * changePassword method is used to change password for the given user.
	 * 
	 * @param authorizationHeader
	 * @param passwordMap
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword(@RequestHeader(value = AUTH_HEADER) String authorizationHeader,
			@RequestBody Map<String, String> passwordMap) {
		Customer customer = tokenUtil.getCustomer(authorizationHeader);
		return ResponseEntity.ok(customerService.changePassword(customer, passwordMap));
	}

}
