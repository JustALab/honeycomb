package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
