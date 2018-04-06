package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.constants.AuthorityName;
import com.honeycakesin.entities.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	String FIND_BY_AUTHORITY_NAME = "SELECT a FROM Authority a WHERE a.name = ?1";

	@Query(FIND_BY_AUTHORITY_NAME)
	Authority findByName(AuthorityName authorityName);

}
