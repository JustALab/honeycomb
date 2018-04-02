package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.OrderFeedback;

@Repository
public interface OrderFeedbackRepository extends JpaRepository<OrderFeedback, Long> {

}
