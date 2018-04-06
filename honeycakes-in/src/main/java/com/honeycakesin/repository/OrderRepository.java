package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.CustomerOrderDto;
import com.honeycakesin.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	String FIND_ALL_BY_CUSTOMER_ID = "SELECT new com.honeycakesin.dto.CustomerOrderDto(co) FROM Order co WHERE co.customer.customerId = ?1";

	@Query(FIND_ALL_BY_CUSTOMER_ID)
	List<CustomerOrderDto> findAllByCustomerId(Long customerId);

}
