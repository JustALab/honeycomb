package com.honeycakesin.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honeycakesin.constants.OrderRating;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "feedbackTime" }, allowGetters = true)
@Table(name = "order_feedback")
public class OrderFeedback implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long feedbackId;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "order_number")
	Order order;
	
	/** integer representation of rating from 1 to 5. */
	OrderRating orderRating;
	
	@Column(nullable = true)
	String comments;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	Date feedbackDateTime;
	
}
