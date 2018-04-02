package com.honeycakesin.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.honeycakesin.constants.ItemCategory;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
public class Item implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long itemId;

	@Column(nullable = false)
	String itemName;

	@Enumerated(EnumType.STRING)
	ItemCategory itemCategory;

	@Column(nullable = false)
	Double itemPrice;

	@Column(nullable = false)
	Integer quantitySlab;

	/**
	 * VendorItemsDto is join table representing a ManyToMany relationship between
	 * VendorDto & ItemDto. Since, VendorItemsDto has an extra
	 * column(availability_status), the relationship is broken into a OneToMany
	 * relationship and a ManyToOne relationship.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "item")
	Set<VendorItems> vendorItemsSet;

}
