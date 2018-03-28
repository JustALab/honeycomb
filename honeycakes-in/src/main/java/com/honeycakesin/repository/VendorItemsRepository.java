package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.VendorItemsDto;

@Repository
public interface VendorItemsRepository extends JpaRepository<VendorItemsDto, Long>{

}
