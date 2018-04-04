package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.entities.VendorItems;

@Repository
public interface VendorItemsRepository extends JpaRepository<VendorItems, Long> {

	String FIND_ALL_BY_VENDOR_ID = "SELECT new com.honeycakesin.dto.VendorItemsDto(vi) FROM VendorItems vi WHERE vi.vendor.vendorId = ?1";

	@Query(FIND_ALL_BY_VENDOR_ID)
	List<VendorItemsDto> findAllByVendorId(Long vendorId);

}
