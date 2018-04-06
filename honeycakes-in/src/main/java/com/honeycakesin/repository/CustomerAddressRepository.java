package com.honeycakesin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.constants.DeliveryAddressType;
import com.honeycakesin.entities.CustomerAddress;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

	String FIND_BY_CUSTOMERID_DELIVERY_ADDRESS_TYPE = "SELECT ca FROM CustomerAddress ca WHERE ca.customer.customerId = ?1 AND ca.deliveryAddressType = ?2";

	@Query(FIND_BY_CUSTOMERID_DELIVERY_ADDRESS_TYPE)
	Optional<CustomerAddress> findByCustomerIdAndDeliveryAddressType(Long customerId, DeliveryAddressType deliveryAddressType);

}
