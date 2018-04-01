package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.dto.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Long>{

	UserDto findByUsername(String username);
	
}
