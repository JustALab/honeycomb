package com.honeycakesin.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "vendors")
public class Vendor implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long vendorId;

	@Column(nullable = false)
	String vendorName;

	@Column(nullable = false)
	String address;

	@Column(nullable = false)
	String contactMobile;

	@Column(nullable = true)
	String contactEmail;

	/**
	 * VendorItemsDto is join table representing a ManyToMany relationship between
	 * VendorDto & ItemDto. Since, VendorItemsDto has an extra
	 * column(availability_status), the relationship is broken into a OneToMany
	 * relationship and a ManyToOne relationship.
	 */
	@OneToMany(mappedBy = "vendorDto")
	Set<VendorItems> vendorItemsDtoSet;
	
	@OneToMany(mappedBy = "vendorDto")
	Set<Order> orderDtoSet;

}
