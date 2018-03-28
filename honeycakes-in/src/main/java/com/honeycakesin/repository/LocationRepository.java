package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.LocationDto;

@Repository
public interface LocationRepository extends JpaRepository<LocationDto, Long> {

}
