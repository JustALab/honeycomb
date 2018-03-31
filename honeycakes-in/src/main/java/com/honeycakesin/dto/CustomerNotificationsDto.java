package com.honeycakesin.dto;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honeycakesin.constants.CustomerNotificationType;
import com.honeycakesin.constants.NotificationDeliveryType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "notificationDateTime" }, allowGetters = true)
@Table(name = "customer_notifications")
public class CustomerNotificationsDto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long notificationId;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	CustomerDto customerDto;
	
	@Enumerated(EnumType.STRING)
	CustomerNotificationType notificationType;
	
	@Enumerated(EnumType.STRING)
	NotificationDeliveryType deliveryType;
	
	String messageContent;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	Date notificationDateTime;
	
}
