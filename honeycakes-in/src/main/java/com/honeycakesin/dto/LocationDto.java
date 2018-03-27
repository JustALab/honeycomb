package com.honeycakesin.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Entity
@Table(name = "locations")
public class LocationDto implements Serializable {

	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	@Column(nullable = false)
	String locationName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "vendor_code")
	VendorDto deliveryVendor;

}
