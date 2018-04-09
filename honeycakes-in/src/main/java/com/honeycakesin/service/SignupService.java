package com.honeycakesin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.honeycakesin.constants.AuthorityName;
import com.honeycakesin.constants.NotificationDeliveryType;
import com.honeycakesin.constants.NotificationStatus;
import com.honeycakesin.constants.NotificationType;
import com.honeycakesin.constants.NotificationUserType;
import com.honeycakesin.constants.SmsOrEmailStatus;
import com.honeycakesin.constants.UserSignupStatus;
import com.honeycakesin.constants.VerificationStatus;
import com.honeycakesin.dto.SignupDto;
import com.honeycakesin.dto.UserSignupMessageDto;
import com.honeycakesin.entities.Authority;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.Notification;
import com.honeycakesin.entities.User;
import com.honeycakesin.repository.AuthorityRepository;
import com.honeycakesin.repository.CustomerRepository;
import com.honeycakesin.repository.NotificationRepository;
import com.honeycakesin.repository.UserRepository;
import com.honeycakesin.util.NotificationSender;
import com.honeycakesin.util.OtpGenerator;
import com.honeycakesin.vo.MobileVerificationVo;

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

	NotificationSender notificationSender;

	NotificationRepository notificationRepository;

	String EMAIL_ALREADY_EXISTS = "Email already exists.";

	String MOBILE_ALREADY_EXISTS = "Mobile number already exists.";

	String USER_ADDED_SUCCESSFULLY = "User created successfully.";

	String UNKNOWN_ERROR = "Unknown error!";

	/**
	 * addNewCustomer method is used to add a new customer. The method first checks
	 * if the mobile number given already exists. If not, it creates a user record
	 * and then creates the customer record otherwise, FAILURE status is sent as a
	 * response. It also send OTP to user mobile for mobile verification.
	 * 
	 * @param signupDto
	 * @return UserSignupMessageDto
	 */
	public UserSignupMessageDto addNewCustomer(SignupDto signupDto) {
		UserSignupMessageDto userSignupMessageDto;
		Customer checkCustomer = checkIfCustomerExistsBasedOnMobile(signupDto);
		if (Objects.nonNull(checkCustomer)) {
			userSignupMessageDto = new UserSignupMessageDto();
			userSignupMessageDto.setSignupStatus(UserSignupStatus.MOBILE_NUMBER_EXISTS);
			userSignupMessageDto.setMessage(MOBILE_ALREADY_EXISTS);
			userSignupMessageDto.setEmail(checkCustomer.getEmail());
			userSignupMessageDto.setEmailVerificationStatus(checkCustomer.getEmailVerificationStatus());
			if(checkCustomer.getEmailVerificationStatus() != VerificationStatus.VERIFIED) {
				//email call 
				userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.SENT);
			} else {
				userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.NOT_SENT);
			}
			userSignupMessageDto.setMobile(checkCustomer.getMobile());
			userSignupMessageDto.setMobileVerificationStatus(checkCustomer.getMobileVerificationStatus());
			if(checkCustomer.getMobileVerificationStatus() != VerificationStatus.VERIFIED) {
				if (sendOtp(checkCustomer.getEmail(), checkCustomer.getMobile(), NotificationUserType.CUSTOMER)) {
					userSignupMessageDto.setOtpStatus(SmsOrEmailStatus.SENT);
				} else {
					userSignupMessageDto.setOtpStatus(SmsOrEmailStatus.NOT_SENT);
				}
			} else {
				userSignupMessageDto.setOtpStatus(SmsOrEmailStatus.NOT_SENT);
			}
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
				userSignupMessageDto.setEmail(customer.getEmail());
				userSignupMessageDto.setEmailVerificationStatus(customer.getEmailVerificationStatus());
				if(customer.getEmailVerificationStatus() != VerificationStatus.VERIFIED) {
					//email call 
					userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.SENT);
				} else {
					userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.NOT_SENT);
				}
				userSignupMessageDto.setMobile(customer.getMobile());
				userSignupMessageDto.setMobileVerificationStatus(customer.getMobileVerificationStatus());
				if(customer.getMobileVerificationStatus() != VerificationStatus.VERIFIED) {
					if (sendOtp(customer.getEmail(), customer.getMobile(), NotificationUserType.CUSTOMER)) {
						userSignupMessageDto.setOtpStatus(SmsOrEmailStatus.SENT);
					} else {
						userSignupMessageDto.setOtpStatus(SmsOrEmailStatus.NOT_SENT);
					}
				} else {
					userSignupMessageDto.setOtpStatus(SmsOrEmailStatus.NOT_SENT);
				}
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
		User checkUser = checkIfUserExists(signupDto);
		if (Objects.nonNull(checkUser)) {
			Customer customer = getCustomerByEmail(checkUser.getEmail());
			if (Objects.nonNull(customer)) {
				userSignupMessageDto.setSignupStatus(UserSignupStatus.EMAIL_EXISTS);
				userSignupMessageDto.setMessage(EMAIL_ALREADY_EXISTS);
				userSignupMessageDto.setEmail(customer.getEmail());
				userSignupMessageDto.setEmailVerificationStatus(customer.getEmailVerificationStatus());
				userSignupMessageDto.setMobile(customer.getMobile());
				userSignupMessageDto.setMobileVerificationStatus(customer.getMobileVerificationStatus());
			}
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
		user.setEnabled(false);
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
			userSignupMessageDto.setSignupStatus(UserSignupStatus.UNKOWN_FAILURE);
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
	private Customer checkIfCustomerExistsBasedOnMobile(SignupDto signupDto) {
		return customerRepository.findCustomerByMobile(signupDto.getMobile());
	}

	/**
	 * checkIfUserExists method checks if user already exists based on the email.
	 * 
	 * @param signupDto
	 * @return User
	 */
	private User checkIfUserExists(SignupDto signupDto) {
		return userRepository.findByUsername(signupDto.getEmail());
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

	/**
	 * getCustomerByEmail method is used to get the Customer based on the email.
	 * 
	 * @param email
	 * @return Customer
	 */
	private Customer getCustomerByEmail(String email) {
		return customerRepository.findCustomerByEmail(email);
	}

	/**
	 * sendOtp method send the OTP for the user. It saves the OTP sent to the user
	 * in the database.
	 * 
	 * @param email
	 * @param mobile
	 * @return boolean
	 */
	private boolean sendOtp(String email, String mobile, NotificationUserType notificationUserType) {
		String otp = OtpGenerator.generate();
		String message = "OTP is " + otp + " for signing up with Honeycakes. Order and enjoy your cake today!";
		String subject = "Mobile Verification";
		NotificationStatus status = notificationSender.notifyUser(mobile, NotificationDeliveryType.SMS, subject,
				message);

		// test line
		//// NotificationStatus status1 = notificationSender.notifyUser(email,
		//// NotificationDeliveryType.EMAIL, subject,
		// message);
		if (status == NotificationStatus.SUCCESS) {
			Notification notification = new Notification();
			notification.setDeliveryType(NotificationDeliveryType.SMS);
			notification.setNotificationType(NotificationType.OTP);
			notification.setMessage(message);
			notification.setUserType(notificationUserType);
			notification.setUserEmail(email);
			notification.setUserMobile(mobile);
			notificationRepository.save(notification);
			return true;
		}
		return false;
	}

	/**
	 * verifyMobileNumber method is used to verify mobile user mobile number by user
	 * provided OTP and the one that is available in the database. It changes the
	 * mobileVerificationStatus to VERIFIED and it also updates the enabled field of
	 * the users table, so that the users can login t the systam.
	 * 
	 * @param mobileVerificationVo
	 * @return VerificationStatus
	 */
	public VerificationStatus verifyMobileNumber(MobileVerificationVo mobileVerificationVo) {
		Notification notification = notificationRepository
				.findByCustomerMobile(mobileVerificationVo.getMobile(), NotificationType.OTP).get(0);
		if (notification.getMessage().contains(mobileVerificationVo.getOtp())) {
			Customer customer = getCustomerByEmail(mobileVerificationVo.getEmail());
			customer.setMobileVerificationStatus(VerificationStatus.VERIFIED);
			customerRepository.save(customer);
			User user = userRepository.findByUsername(mobileVerificationVo.getEmail());
			user.setEnabled(true);
			userRepository.save(user);
			return VerificationStatus.VERIFIED;
		}
		return VerificationStatus.NOT_VERIFIED;
	}
}