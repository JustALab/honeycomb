package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.CustomerDto;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDto, Long> {

}
