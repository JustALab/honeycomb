package com.honeycakesin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeycakesin.constants.FeedbackStatus;
import com.honeycakesin.constants.OrderStatus;
import com.honeycakesin.dto.CustomerDto;
import com.honeycakesin.dto.CustomerOrderDto;
import com.honeycakesin.dto.CustomerOrderItemsDto;
import com.honeycakesin.dto.LocationDto;
import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.Order;
import com.honeycakesin.entities.OrderItems;
import com.honeycakesin.repository.CustomerRepository;
import com.honeycakesin.repository.ItemRepository;
import com.honeycakesin.repository.LocationRepository;
import com.honeycakesin.repository.OrderRepository;
import com.honeycakesin.repository.VendorItemsRepository;
import com.honeycakesin.repository.VendorRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author Ramu Ramasamy
 * 
 */
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

	CustomerRepository customerRepository;

	LocationRepository locationRepository;

	VendorItemsRepository vendorItemsRepository;
	
	OrderRepository orderRepository;
	
	VendorRepository vendorRepository;
	
	ItemRepository itemRepository;

	/**
	 * getCustomer method is used to get the user data from the CUSTOMERS table.
	 * 
	 * @param username
	 *            which is a email address of the user available in the USERS table.
	 * @return
	 */
	public CustomerDto getCustomer(String username) {
		return customerRepository.findByEmail(username);
	}

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return List of locations.
	 */
	public List<LocationDto> getLocationList() {
		return locationRepository.findAllLocationsWithOnlyVendorId();
	}

	/**
	 * getVendorItemsList method returns a list of items available for the specified
	 * vendor ID.
	 * 
	 * @param vendorId
	 * @return List of items available with the vendor.
	 */
	public List<VendorItemsDto> getVendorItemsList(Long vendorId) {
		return vendorItemsRepository.findAllByVendorId(vendorId);
	}

	public Order placeOrder(Customer customer, CustomerOrderDto customerOrderDto) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setVendor(vendorRepository.findVendorById(customerOrderDto.getVendorId()));
		order.setDeliveryDate(customerOrderDto.getDeliveryDate());
		order.setDeliveryTime(customerOrderDto.getDeliveryTime());
		order.setTotalAmount(customerOrderDto.getTotalAmount());
		order.setPaymentMode(customerOrderDto.getPaymentMode());
		order.setDeliveryToAddressType(customerOrderDto.getDeliveryToAddressType());
		order.setFeedbackStatus(FeedbackStatus.NOT_SUBMITTED);
		order.setOrderStatus(OrderStatus.PLACED);		
		List<OrderItems> orderItemsEntityList = new ArrayList<>();
		List<CustomerOrderItemsDto> customerOrderItemsDtoList = customerOrderDto.getOrderItemsList();
		customerOrderItemsDtoList.stream().forEach(orderItemDto -> {
			OrderItems orderItemsEntity = new OrderItems();
			orderItemsEntity.setPrice(orderItemDto.getPrice());
			orderItemsEntity.setQuantity(orderItemDto.getQuantity());
			orderItemsEntity.setItem(itemRepository.findItemByItemId(orderItemDto.getItemId()));
			orderItemsEntity.setOrder(order);
			orderItemsEntityList.add(orderItemsEntity);
		});
		order.setOrderItemsList(orderItemsEntityList);
		return orderRepository.save(order);
	}

}
