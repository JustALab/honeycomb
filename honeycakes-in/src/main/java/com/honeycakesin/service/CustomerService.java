package com.honeycakesin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.honeycakesin.constants.DeliveryAddressType;
import com.honeycakesin.constants.FeedbackStatus;
import com.honeycakesin.constants.OrderStatus;
import com.honeycakesin.constants.PasswordChangeStatus;
import com.honeycakesin.dto.CustomerDto;
import com.honeycakesin.dto.CustomerOrderDto;
import com.honeycakesin.dto.CustomerOrderItemsDto;
import com.honeycakesin.dto.LocationDto;
import com.honeycakesin.dto.OrderFeedbackDto;
import com.honeycakesin.dto.VendorItemsDto;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.CustomerAddress;
import com.honeycakesin.entities.Item;
import com.honeycakesin.entities.Order;
import com.honeycakesin.entities.OrderFeedback;
import com.honeycakesin.entities.OrderItems;
import com.honeycakesin.entities.User;
import com.honeycakesin.repository.CustomerAddressRepository;
import com.honeycakesin.repository.CustomerRepository;
import com.honeycakesin.repository.ItemRepository;
import com.honeycakesin.repository.LocationRepository;
import com.honeycakesin.repository.OrderFeedbackRepository;
import com.honeycakesin.repository.OrderRepository;
import com.honeycakesin.repository.UserRepository;
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

	CustomerAddressRepository customerAddressRepository;

	LocationRepository locationRepository;

	VendorItemsRepository vendorItemsRepository;

	OrderRepository orderRepository;

	VendorRepository vendorRepository;

	ItemRepository itemRepository;

	OrderFeedbackRepository orderFeedbackRepository;

	UserRepository userRepository;

	/**
	 * getCustomer method is used to get the user data from the CUSTOMERS table.
	 * 
	 * @param username
	 *            which is a mobile number of the user available in the USERS table.
	 * @return CustomerDto
	 */
	public CustomerDto getCustomer(String username) {
		return customerRepository.findByMobile(username);
	}

	/**
	 * getLocationList method returns a list of locations available.
	 * 
	 * @return locationsList.
	 */
	public List<LocationDto> getLocationList() {
		return locationRepository.findAllLocationsWithOnlyVendorId();
	}

	/**
	 * getVendorItemsList method returns a list of items available for the specified
	 * vendor ID.
	 * 
	 * @param vendorId
	 * @return vendorItemsDtoList.
	 */
	public List<VendorItemsDto> getVendorItemsList(Long vendorId) {
		return vendorItemsRepository.findAllByVendorId(vendorId);
	}

	/**
	 * placeOrder method computes the price values from the db, then checks if the
	 * customerAdress already exists before placing the order. If customer address
	 * already exists, the address is updated otherwise a new customerAddress object
	 * is created.
	 * 
	 * @param customer
	 * @param customerOrderDto
	 * @return Order
	 */
	public Order placeOrder(Customer customer, CustomerOrderDto customerOrderDto) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setVendor(vendorRepository.findVendorById(customerOrderDto.getVendorId()));
		order.setDeliveryDate(customerOrderDto.getDeliveryDate());
		order.setDeliveryTime(customerOrderDto.getDeliveryTime());
		order.setPaymentMode(customerOrderDto.getPaymentMode());
		order.setDeliveryAddressType(customerOrderDto.getDeliveryAddressType());
		order.setDeliveryAddress(customerOrderDto.getDeliveryAddress());
		order.setFeedbackStatus(FeedbackStatus.NOT_SUBMITTED);
		order.setOrderStatus(OrderStatus.PLACED);
		// reset the total amount to 0. It will be computed below.
		customerOrderDto.setTotalAmount(0d);
		List<OrderItems> orderItemsEntityList = new ArrayList<>();
		List<CustomerOrderItemsDto> customerOrderItemsDtoList = customerOrderDto.getOrderItemsList();
		customerOrderItemsDtoList.stream().forEach(orderItemDto -> {
			Long itemId = orderItemDto.getItemId();
			Double itemPriceForQuantity = computeItemPrice(itemId, orderItemDto);
			// add the item prices
			customerOrderDto.setTotalAmount(customerOrderDto.getTotalAmount() + itemPriceForQuantity);
			OrderItems orderItemsEntity = new OrderItems();
			orderItemsEntity.setPrice(itemPriceForQuantity);
			orderItemsEntity.setQuantity(orderItemDto.getQuantity());
			orderItemsEntity.setItem(itemRepository.findItemByItemId(itemId));
			orderItemsEntity.setOrder(order);
			orderItemsEntityList.add(orderItemsEntity);
		});
		order.setTotalAmount(customerOrderDto.getTotalAmount());
		order.setOrderItemsList(orderItemsEntityList);

		// save or update only HOME and OFFICE address.
		if (customerOrderDto.getDeliveryAddressType() != DeliveryAddressType.OTHER) {
			Optional<CustomerAddress> customerAddressOptional = getCustomerAddressIfExists(customer.getCustomerId(),
					customerOrderDto.getDeliveryAddressType());
			CustomerAddress customerAddress = customerAddressOptional.orElse(new CustomerAddress());
			customerAddress.setCustomer(customer);
			customerAddress.setDeliveryAddressType(customerOrderDto.getDeliveryAddressType());
			customerAddress.setAddress(customerOrderDto.getDeliveryAddress());
			customerAddressRepository.save(customerAddress);
		}
		return orderRepository.save(order);
	}

	/**
	 * computeItemPrice method computes the price for the item based on the item
	 * price available in the db & the given quantity.
	 * 
	 * @param itemId
	 * @param orderItemDto
	 * @return itemPriceForQuantity
	 */
	private Double computeItemPrice(Long itemId, CustomerOrderItemsDto orderItemDto) {
		Double itemPrice = getItemPrice(itemId);
		if (Objects.nonNull(itemPrice)) {
			Double itemPriceForQuantity = orderItemDto.getQuantity() * itemPrice;
			return itemPriceForQuantity;
		}
		return itemPrice;
	}

	/**
	 * getItemPrice method gets the item price based on the given itemId.
	 * 
	 * @param itemId
	 * @return itemPrice
	 */
	private Double getItemPrice(Long itemId) {
		Optional<Item> itemOptional = itemRepository.findById(itemId);
		if (itemOptional.isPresent()) {
			Item item = itemOptional.get();
			return item.getItemPrice();
		}
		return null;
	}

	/**
	 * getCustomerAddressIfExists method fetches the customer address for a given
	 * customerId and deliveryAddressType
	 * 
	 * @param customerId
	 * @param deliveryAddressType
	 * @return CustomerAddress
	 */
	private Optional<CustomerAddress> getCustomerAddressIfExists(Long customerId,
			DeliveryAddressType deliveryAddressType) {
		return customerAddressRepository.findByCustomerIdAndDeliveryAddressType(customerId, deliveryAddressType);
	}

	/**
	 * getOrderHistory method gets the list of orders made by the customer.
	 * 
	 * @param customer
	 * @return customerOrderDtoList
	 */
	public List<CustomerOrderDto> getOrderHistory(Customer customer) {
		return orderRepository.findAllByCustomerId(customer.getCustomerId());
	}

	/**
	 * submitOrderFeedback method is used to submit the order feedback for the
	 * customer. It also updates the feedbackStatus field to 'SUBMITTED'.
	 * 
	 * @param orderNumber
	 * @param orderFeedbackDto
	 * @return OrderFeedback
	 */
	public OrderFeedback submitOrderFeedback(Long orderNumber, OrderFeedbackDto orderFeedbackDto) {
		Optional<Order> orderOptional = orderRepository.findById(orderNumber);
		if (orderOptional.isPresent()) {
			Order order = orderOptional.get();
			OrderFeedback orderFeedback = new OrderFeedback();
			orderFeedback.setOrder(order);
			orderFeedback.setOrderRating(orderFeedbackDto.getOrderRating());
			orderFeedback.setComments(orderFeedbackDto.getComments());
			order.setFeedbackStatus(FeedbackStatus.SUBMITTED);
			orderRepository.save(order);
			return orderFeedbackRepository.save(orderFeedback);
		}
		return null;
	}

	/**
	 * deleteAddress method deletes the address with the given addressId.
	 * 
	 * @param addressId
	 */
	public void deleteAddress(Long addressId) {
		customerAddressRepository.deleteById(addressId);
	}

	/**
	 * changePassword method is used to change password for the given user.
	 * 
	 * @param customer
	 * @param passwordMap
	 * @return PasswordChangeStatus
	 */
	public PasswordChangeStatus changePassword(Customer customer, Map<String, String> passwordMap) {
		User user = userRepository.findByUsername(customer.getMobile());
		BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
		String oldPassword = passwordMap.get("oldPassword");
		if (BCrypt.checkpw(oldPassword, user.getPassword())) {
			String newPassword = bcryptEncoder.encode(passwordMap.get("newPassword"));
			user.setPassword(newPassword);
			userRepository.save(user);
			return PasswordChangeStatus.SUCCESS;
		} else {
			return PasswordChangeStatus.INCORRECT_OLD_PASSWORD;
		}
	}

}
