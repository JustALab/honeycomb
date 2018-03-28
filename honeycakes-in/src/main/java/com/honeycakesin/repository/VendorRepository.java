package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.VendorDto;

@Repository
public interface VendorRepository extends JpaRepository<VendorDto, Long>{

}
