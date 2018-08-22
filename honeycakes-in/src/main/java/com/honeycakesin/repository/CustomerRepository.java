package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.CustomerDto;
import com.honeycakesin.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	String FIND_BY_EMAIL = "SELECT new com.honeycakesin.dto.CustomerDto(c) FROM Customer c WHERE c.email = ?1";

	String FIND_BY_MOBILE = "SELECT new com.honeycakesin.dto.CustomerDto(c) FROM Customer c WHERE c.mobile = ?1";

	String FIND_CUSTOMER_BY_EMAIL = "SELECT c FROM Customer c WHERE c.email = ?1";

	String FIND_CUSTOMER_BY_MOBILE = "SELECT c FROM Customer c WHERE c.mobile = ?1";

	@Query(FIND_BY_EMAIL)
	CustomerDto findByEmail(String email);

	@Query(FIND_BY_MOBILE)
	CustomerDto findByMobile(String mobile);

	@Query(FIND_CUSTOMER_BY_EMAIL)
	Customer findCustomerByEmail(String email);

	@Query(FIND_CUSTOMER_BY_MOBILE)
	Customer findCustomerByMobile(String mobile);

}
