package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	String FIND_BY_CUSTOMER_MOBILE = "SELECT n FROM Notification n WHERE n.userMobile = ?1 AND n.notificationType = \"OTP\" ORDER BY n.notificationDateTime DESC";

	@Query(FIND_BY_CUSTOMER_MOBILE)
	List<Notification> findByCustomerMobile(String mobile);

}
