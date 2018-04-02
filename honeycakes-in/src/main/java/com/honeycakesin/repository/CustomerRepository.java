package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
