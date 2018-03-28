package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.OrderDto;

@Repository
public interface OrderRepository extends JpaRepository<OrderDto, Long>{

}
