package com.honeycakesin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.Vendor;
import com.honeycakesin.repository.CustomerRepository;
import com.honeycakesin.repository.VendorRepository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Ramu Ramasamy
 *
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomJwtTokenUtil {

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	VendorRepository vendorRepository;

	/**
	 * getUsernameFromToken method fetches username for the specified token.
	 * 
	 * @param authorizationHeader
	 * @return String username which is a email
	 */
	public String getUsernameFromToken(String authorizationHeader) {
		return jwtTokenUtil.getUsernameFromToken(splitAuthorizationHeader(authorizationHeader));
	}

	/**
	 * getCustomerId method fetches the customerId based on the username available
	 * in the token.
	 * 
	 * @param authorizationHeader
	 * @return Long userId
	 */
	public Customer getCustomer(String authorizationHeader) {
		String username = jwtTokenUtil.getUsernameFromToken(splitAuthorizationHeader(authorizationHeader));
		return customerRepository.findCustomerByMobile(username);
	}

	/**
	 * getVendorId method fetched vendorId based on the username available in the
	 * token.
	 * 
	 * @param authorizationHeader
	 * @return Long vendorId
	 */
	public Vendor getVendor(String authorizationHeader) {
		String username = jwtTokenUtil.getUsernameFromToken(splitAuthorizationHeader(authorizationHeader));
		return vendorRepository.findVendorByEmail(username);
	}

	/**
	 * returnSubstring method splits the token having 'Bearer ' text from the
	 * Authorization header.
	 * 
	 * @param authorizationHeader
	 * @return String token
	 */
	public String splitAuthorizationHeader(String authorizationHeader) {
		return authorizationHeader.substring(7);
	}

}
