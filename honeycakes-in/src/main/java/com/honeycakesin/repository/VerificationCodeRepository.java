package com.honeycakesin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honeycakesin.constants.NotificationDeliveryType;
import com.honeycakesin.entities.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

	String FIND_BY_MOBILE = "SELECT vc FROM VerificationCode vc WHERE vc.deliveryType = ?1 AND vc.deliveryTo = ?2 ORDER BY vc.verificationCodeId DESC";

	@Query(FIND_BY_MOBILE)
	List<VerificationCode> findLatestCode(NotificationDeliveryType deliveryType, String deliveryTo);

}
