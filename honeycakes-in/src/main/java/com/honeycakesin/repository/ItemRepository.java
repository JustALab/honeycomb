package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	String FIND_ITEM_BY_ID = "SELECT i FROM Item i WHERE i.itemId = ?1";
	
	@Query(FIND_ITEM_BY_ID)
	Item findItemByItemId(Long itemId);

}
