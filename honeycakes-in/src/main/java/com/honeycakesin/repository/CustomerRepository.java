package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.CustomerDto;
import com.honeycakesin.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	String FIND_BY_EMAIL = "SELECT new com.honeycakesin.dto.CustomerDto(c) FROM Customer c WHERE c.email = ?1";
	
	@Query(FIND_BY_EMAIL)
	CustomerDto findByEmail(String email);
	
}
