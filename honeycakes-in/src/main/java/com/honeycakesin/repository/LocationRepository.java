package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	
	String FIND_ALL_WITH_ONLY_VENDOR_ID = "SELECT new com.honeycakesin.dto.LocationDto(l) FROM Location l ";
	
	@Query(FIND_ALL_WITH_ONLY_VENDOR_ID)
	List<Location> findAllLocationsWithOnlyVendorId();
	
}
