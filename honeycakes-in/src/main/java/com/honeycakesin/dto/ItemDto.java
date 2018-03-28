package com.honeycakesin.dto;

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

import com.honeycakesin.utils.ItemCategory;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
public class ItemDto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long itemId;

	@Column(nullable = false)
	String itemName;

	@Enumerated(EnumType.STRING)
	ItemCategory itemCategory;

	@Column(nullable = false)
	double itemPrice;

	@Column(nullable = false)
	int quantitySlab;

	/**
	 * VendorItemsDto is join table representing a ManyToMany relationship between
	 * VendorDto & ItemDto. Since, VendorItemsDto has an extra
	 * column(availability_status), the relationship is broken into a OneToMany
	 * relationship and a ManyToOne relationship.
	 */
	@OneToMany(mappedBy = "itemDto")
	Set<VendorItemsDto> vendorItemsDtoSet;

}
