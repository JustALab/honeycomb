package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
