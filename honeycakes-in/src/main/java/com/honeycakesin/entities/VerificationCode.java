package com.honeycakesin.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honeycakesin.constants.NotificationDeliveryType;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "generatedTime" }, allowGetters = true)
@Table(name = "verification_codes")
public class VerificationCode implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long verificationCodeId;

	@Enumerated(EnumType.STRING)
	NotificationDeliveryType deliveryType;

	String deliveryTo;

	String otp;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	Date generatedTime;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	Date expirationTime;

}
