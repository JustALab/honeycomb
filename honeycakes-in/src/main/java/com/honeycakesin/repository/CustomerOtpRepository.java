package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.CustomerOtpDto;

@Repository
public interface CustomerOtpRepository extends JpaRepository<CustomerOtpDto, Long> {

}
