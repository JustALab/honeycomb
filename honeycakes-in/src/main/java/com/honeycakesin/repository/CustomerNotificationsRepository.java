package com.honeycakesin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.CustomerNotifications;

@Repository
public interface CustomerNotificationsRepository extends JpaRepository<CustomerNotifications, Long> {

}
