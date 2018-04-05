package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{
	
	String FIND_VENDOR_BY_EMAIL = "SELECT v FROM Vendor v WHERE v.contactEmail = ?1";
	
	String FIND_VENDOR_BY_ID = "SELECT v FROM Vendor v WHERE v.vendorId = ?1";
	
	@Query(FIND_VENDOR_BY_EMAIL)
	Vendor findVendorByEmail(String email);
	
	@Query(FIND_VENDOR_BY_ID)
	Vendor findVendorById(Long vendorId);
	
}
