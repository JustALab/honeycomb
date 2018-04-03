package com.honeycakesin.dto;

import java.util.List;

import com.honeycakesin.constants.VerificationStatus;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.CustomerAddress;

import lombok.Data;

@Data
public class CustomerDto {

	Long customerId;

	String firstName;

	String lastName;

	String email;

	String mobile;

	VerificationStatus emailVerificationStatus;

	VerificationStatus mobileVerificationStatus;

	List<CustomerAddress> customerAddressList;

	public CustomerDto(Customer customer) {

		this.customerId = customer.getCustomerId();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.email = customer.getEmail();
		this.mobile = customer.getMobile();
		this.emailVerificationStatus = customer.getEmailVerificationStatus();
		this.mobileVerificationStatus = customer.getMobileVerificationStatus();
		this.customerAddressList = customer.getCustomerAddressList();

	}

}
