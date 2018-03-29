package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.LocationDto;

@Repository
public interface LocationRepository extends JpaRepository<LocationDto, Long> {
	
	String FIND_ALL_WITH_ONLY_VENDOR_ID = "SELECT new com.honeycakesin.filter.LocationFilter(l) FROM LocationDto l ";
	
	@Query(FIND_ALL_WITH_ONLY_VENDOR_ID)
	List<LocationDto> findAllLocationsWithOnlyVendorId();
	
}
