package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.CustomerAddressDto;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddressDto, Long> {

}
