package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
