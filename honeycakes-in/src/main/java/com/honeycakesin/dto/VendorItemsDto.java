package com.honeycakesin.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.honeycakesin.utils.AvailabilityStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "vendor_items")
public class VendorItemsDto implements Serializable {
	/**
	 * VendorItemsDto is join table representing a ManyToMany relationship between
	 * VendorDto & ItemDto. Since, VendorItemsDto has an extra
	 * column(availability_status), the relationship is broken into a OneToMany
	 * relationship and a ManyToOne relationship.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long vendorItemsId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	VendorDto vendorDto;

	@ManyToOne
	@JoinColumn(name = "item_id")
	ItemDto itemDto;

	@Enumerated(EnumType.STRING)
	AvailabilityStatus availabilityStatus;

}
