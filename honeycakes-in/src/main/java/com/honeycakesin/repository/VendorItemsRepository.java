package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.VendorItemsDto;

@Repository
public interface VendorItemsRepository extends JpaRepository<VendorItemsDto, Long> {

	String FIND_ALL_BY_VENDOR_ID = "SELECT new com.honeycakesin.filter.VendorItemsFilter(vi) FROM VendorItemsDto vi WHERE vi.vendorDto.vendorId = ?1";

	@Query(FIND_ALL_BY_VENDOR_ID)
	List<VendorItemsDto> findAllByVendorId(Long vendorId);

}
