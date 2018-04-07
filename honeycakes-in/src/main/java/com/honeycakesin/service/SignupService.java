package com.honeycakesin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.honeycakesin.constants.AuthorityName;
import com.honeycakesin.constants.UserSignupStatus;
import com.honeycakesin.constants.VerificationStatus;
import com.honeycakesin.dto.SignupDto;
import com.honeycakesin.dto.UserSignupMessageDto;
import com.honeycakesin.entities.Authority;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.User;
import com.honeycakesin.repository.AuthorityRepository;
import com.honeycakesin.repository.CustomerRepository;
import com.honeycakesin.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SignupService {

	UserRepository userRepository;

	CustomerRepository customerRepository;

	AuthorityRepository authorityRepository;

	String EMAIL_ALREADY_EXISTS = "Email already exists.";

	String MOBILE_ALREADY_EXISTS = "Mobile number already exists.";

	String USER_ADDED_SUCCESSFULLY = "User created successfully.";

	String UNKNOWN_ERROR = "Unknown error!";

	/**
	 * addNewCustomer method is used to add a new customer. The method first checks
	 * if the mobile number given already exists. If not, it creates a user record
	 * and then creates the customer record otherwise, FAILURE status is sent as a
	 * response.
	 * 
	 * @param signupDto
	 * @return boolean
	 */
	public UserSignupMessageDto addNewCustomer(SignupDto signupDto) {
		UserSignupMessageDto userSignupMessageDto;
		if (checkIfCustomerExistsBasedOnMobile(signupDto)) {
			userSignupMessageDto = new UserSignupMessageDto();
			userSignupMessageDto.setSignupStatus(UserSignupStatus.FAILURE);
			userSignupMessageDto.setMessage(MOBILE_ALREADY_EXISTS);
			return userSignupMessageDto;
		} else {
			userSignupMessageDto = addNewUser(signupDto, AuthorityName.ROLE_CUSTOMER);
			if (userSignupMessageDto.getSignupStatus() == UserSignupStatus.SUCCESS) {
				Customer customer = new Customer();
				customer.setFirstName(signupDto.getFirstName());
				customer.setLastName(signupDto.getLastName());
				customer.setEmail(signupDto.getEmail());
				customer.setMobile(signupDto.getMobile());
				customer.setEmailVerificationStatus(VerificationStatus.NOT_VERIFIED);
				customer.setMobileVerificationStatus(VerificationStatus.NOT_VERIFIED);
				customerRepository.save(customer);
			}
		}
		return userSignupMessageDto;
	}

	/**
	 * addNewUser method is used to add a new user. It checks if the email already
	 * exists. If not, the user record is created otherwise, FAILURE status is sent
	 * as a response.
	 * 
	 * @param signupDto
	 * @param authorityName
	 * @return boolean
	 */
	private UserSignupMessageDto addNewUser(SignupDto signupDto, AuthorityName authorityName) {
		UserSignupMessageDto userSignupMessageDto = new UserSignupMessageDto();
		if (checkIfUserExists(signupDto)) {
			userSignupMessageDto.setSignupStatus(UserSignupStatus.FAILURE);
			userSignupMessageDto.setMessage(EMAIL_ALREADY_EXISTS);
			return userSignupMessageDto;
		}
		BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bcryptEncoder.encode(signupDto.getPassword());
		User user = new User();
		user.setFirstname(signupDto.getFirstName());
		user.setLastname(signupDto.getLastName());
		user.setEmail(signupDto.getEmail());
		user.setUsername(signupDto.getEmail());
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		user.setLastPasswordResetDate(new Date());

		List<Authority> authorities = new ArrayList<>();
		Authority authority = getAuthority(authorityName);
		authorities.add(authority);
		user.setAuthorities(authorities);
		if (Objects.nonNull(userRepository.save(user))) {
			userSignupMessageDto.setSignupStatus(UserSignupStatus.SUCCESS);
			userSignupMessageDto.setMessage(USER_ADDED_SUCCESSFULLY);
			return userSignupMessageDto;
		} else {
			userSignupMessageDto.setSignupStatus(UserSignupStatus.FAILURE);
			userSignupMessageDto.setMessage(UNKNOWN_ERROR);
			return userSignupMessageDto;
		}
	}

	/**
	 * checkIfCustomerExistsBasedOnMobile method checks if the mobile number already
	 * exists.
	 * 
	 * @param signupDto
	 * @return boolean
	 */
	private boolean checkIfCustomerExistsBasedOnMobile(SignupDto signupDto) {
		Customer customer = customerRepository.findCustomerByMobile(signupDto.getMobile());
		if (Objects.nonNull(customer)) {
			return true;
		}
		return false;
	}

	/**
	 * checkIfUserExists method checks if user already exists based on the email.
	 * 
	 * @param signupDto
	 * @return boolean
	 */
	private boolean checkIfUserExists(SignupDto signupDto) {
		User user = userRepository.findByUsername(signupDto.getEmail());
		if (Objects.nonNull(user)) {
			return true;
		}
		return false;
	}

	/**
	 * getAuthority method is used to get the Authority by authorityName.
	 * 
	 * @param authorityName
	 * @return Authority
	 */
	private Authority getAuthority(AuthorityName authorityName) {
		return authorityRepository.findByName(authorityName);
	}

}
