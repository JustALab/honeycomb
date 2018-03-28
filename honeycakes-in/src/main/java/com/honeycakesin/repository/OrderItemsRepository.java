package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.OrderItemsDto;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItemsDto, Long>{

}
