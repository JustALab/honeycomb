package com.honeycakesin.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honeycakesin.constants.VerificationStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "signupDate" }, allowGetters = true)
@Table(name = "customers")
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long customerId;
	
	@Column(nullable = false)
	String firstName;
	
	@Column(nullable = false)
	String lastName;
	
	@Column(unique = true, nullable = false)
	String email;
	
	@Column(unique = true, nullable = false)
	String mobile;
	
	@Enumerated(EnumType.STRING)
	VerificationStatus emailVerificationStatus;
	
	@Enumerated(EnumType.STRING)
	VerificationStatus mobileVerificationStatus;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	Date signupDate;
	
	@OneToMany(mappedBy = "customer")
	List<CustomerAddress> customerAddressList;
	
	@OneToMany(mappedBy = "customer")
	List<Order> orderList;
	
}
