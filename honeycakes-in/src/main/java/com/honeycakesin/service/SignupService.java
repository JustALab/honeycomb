package com.honeycakesin.service;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.honeycakesin.constants.UpdationStatus;
import com.honeycakesin.constants.UserSignupStatus;
import com.honeycakesin.constants.VerificationStatus;
import com.honeycakesin.dto.SignupDto;
import com.honeycakesin.dto.UserSignupMessageDto;
import com.honeycakesin.entities.Authority;
import com.honeycakesin.entities.Customer;
import com.honeycakesin.entities.Notification;
import com.honeycakesin.entities.User;
import com.honeycakesin.entities.VerificationCode;
import com.honeycakesin.repository.AuthorityRepository;
import com.honeycakesin.repository.CustomerRepository;
import com.honeycakesin.repository.NotificationRepository;
import com.honeycakesin.repository.UserRepository;
import com.honeycakesin.repository.VerificationCodeRepository;
import com.honeycakesin.util.NotificationSender;
import com.honeycakesin.util.OtpGenerator;
import com.honeycakesin.vo.MobileVerificationVo;
import com.honeycakesin.vo.SignupMobileUpdateVo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author ram
 *
 */
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SignupService {

	UserRepository userRepository;

	CustomerRepository customerRepository;

	AuthorityRepository authorityRepository;

	NotificationSender notificationSender;

	NotificationRepository notificationRepository;

	VerificationCodeRepository verificationCodeRepository;

	String EMAIL_ALREADY_EXISTS = "Email already exists.";

	String MOBILE_ALREADY_EXISTS = "Mobile number already exists.";

	String USER_ADDED_SUCCESSFULLY = "User created successfully.";

	String UNKNOWN_ERROR = "Unknown error!";

	String MOBILE_LINKED_WITH_OTHER_EMAIL = "Entered mobile number is already linked with another email address.";

	String EMAIL_LINKED_WITH_OTHER_MOBILE = "Entered email address is already linked with another mobile number.";

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
		Customer checkCustomer = getIfCustomerExistsBasedOnMobile(signupDto.getMobile());
		Customer checkCustomerOnEmail = getIfCustomerExistsBasedOnEmail(signupDto.getEmail());

		// if mobile number exists, email address for the corresponding mobile number
		// will be checked. If not equal, MOBILE_LINKED_WITH_OTHER_EMAIL will be
		// returned along with the linked email address.
		if (Objects.nonNull(checkCustomer) && !checkCustomer.getEmail().equals(signupDto.getEmail())) {
			userSignupMessageDto = new UserSignupMessageDto();
			userSignupMessageDto.setSignupStatus(UserSignupStatus.MOBILE_LINKED_WITH_OTHER_EMAIL);
			userSignupMessageDto.setMessage(MOBILE_LINKED_WITH_OTHER_EMAIL);
			userSignupMessageDto.setEmail(checkCustomer.getEmail());
			return userSignupMessageDto;
		}

		// if email already exists, mobile number corresponding to the email will be
		// checked. If not equal, EMAIL_LINKED_WITH_OTHER_MOBILE will be returned along
		// with the linked mobile number.
		if (Objects.nonNull(checkCustomerOnEmail) && !checkCustomerOnEmail.getMobile().equals(signupDto.getMobile())) {
			userSignupMessageDto = new UserSignupMessageDto();
			userSignupMessageDto.setSignupStatus(UserSignupStatus.EMAIL_LINKED_WITH_OTHER_MOBILE);
			userSignupMessageDto.setMessage(EMAIL_LINKED_WITH_OTHER_MOBILE);
			userSignupMessageDto.setMobile(checkCustomerOnEmail.getMobile());
			return userSignupMessageDto;
		}

		if (Objects.nonNull(checkCustomer)) {
			userSignupMessageDto = new UserSignupMessageDto();
			userSignupMessageDto.setSignupStatus(UserSignupStatus.MOBILE_NUMBER_EXISTS);
			userSignupMessageDto.setMessage(MOBILE_ALREADY_EXISTS);
			userSignupMessageDto.setEmail(checkCustomer.getEmail());
			userSignupMessageDto.setEmailVerificationStatus(checkCustomer.getEmailVerificationStatus());
			userSignupMessageDto.setCustomerId(checkCustomer.getCustomerId());
			if (checkCustomer.getEmailVerificationStatus() != VerificationStatus.VERIFIED) {
				// email call
				userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.SENT);
			} else {
				userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.NOT_SENT);
			}
			userSignupMessageDto.setMobile(checkCustomer.getMobile());
			userSignupMessageDto.setMobileVerificationStatus(checkCustomer.getMobileVerificationStatus());
			if (checkCustomer.getMobileVerificationStatus() != VerificationStatus.VERIFIED) {
				if (sendMobileVerificationCode(checkCustomer.getMobile(), NotificationUserType.CUSTOMER)) {
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
				Customer savedCustomer = customerRepository.save(customer);
				userSignupMessageDto.setEmail(customer.getEmail());
				userSignupMessageDto.setEmailVerificationStatus(customer.getEmailVerificationStatus());
				userSignupMessageDto.setCustomerId(savedCustomer.getCustomerId());
				if (customer.getEmailVerificationStatus() != VerificationStatus.VERIFIED) {
					// email call
					userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.SENT);
				} else {
					userSignupMessageDto.setVerificationEmailStatus(SmsOrEmailStatus.NOT_SENT);
				}
				userSignupMessageDto.setMobile(customer.getMobile());
				userSignupMessageDto.setMobileVerificationStatus(customer.getMobileVerificationStatus());
				if (customer.getMobileVerificationStatus() != VerificationStatus.VERIFIED) {
					if (sendMobileVerificationCode(customer.getMobile(), NotificationUserType.CUSTOMER)) {
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
		user.setUsername(signupDto.getMobile());
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
	 * exists. If customer exists it will return the customer.
	 * 
	 * @param signupDto
	 * @return Customer
	 */
	private Customer getIfCustomerExistsBasedOnMobile(String mobile) {
		return customerRepository.findCustomerByMobile(mobile);
	}

	/**
	 * checkIfCustomerExistsBasedOnEmail method checks if the email address already
	 * exists. If customer exists it will return the customer.
	 * 
	 * @param signupDto
	 * @return Customer
	 */
	private Customer getIfCustomerExistsBasedOnEmail(String email) {
		return customerRepository.findCustomerByEmail(email);
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
	 * sendVerificationCode method send the OTP for the user. It saves the OTP sent
	 * to the user in the database.
	 * 
	 * @param email
	 * @param mobile
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	private boolean sendMobileVerificationCode(String mobile, NotificationUserType notificationUserType) {
		String otp = OtpGenerator.generate();
		String message = "OTP is " + otp + " for signing up with Honeycakes. This OTP is valid for 12 HRS.";
		String subject = "Mobile Verification";
		NotificationStatus status = notificationSender.notifyUser(mobile, NotificationDeliveryType.SMS, subject,
				message);
		saveVerificationCode(NotificationDeliveryType.SMS, mobile, otp);

		// test line
		// NotificationStatus status1 = notificationSender.notifyUser(email,
		// NotificationDeliveryType.EMAIL, subject,
		// message);
		if (status == NotificationStatus.SUCCESS) {
			Notification notification = new Notification();
			notification.setDeliveryType(NotificationDeliveryType.SMS);
			notification.setNotificationType(NotificationType.OTP);
			notification.setMessage(message);
			notification.setUserType(notificationUserType);
			notification.setUserMobile(mobile);
			notificationRepository.save(notification);
			return true;
		}
		return false;
	}

	/**
	 * saveVerificationCode method is used to save the verification code to
	 * verification_codes table for completing the verification.
	 * 
	 * @param deliveryType
	 * @param deliveryTo
	 * @param otp
	 */
	private void saveVerificationCode(NotificationDeliveryType deliveryType, String deliveryTo, String otp) {
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setDeliveryType(deliveryType);
		verificationCode.setDeliveryTo(deliveryTo);
		verificationCode.setOtp(otp);
		verificationCode.setExpirationTime(computeOtpExporationTime());
		verificationCodeRepository.save(verificationCode);
	}

	/**
	 * computeOtpExporationTime method is used to compute the OTP expiration time.
	 * 
	 * @return expirationTIme
	 */
	private Date computeOtpExporationTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 12);
		return calendar.getTime();
	}

	/**
	 * verifyMobileNumber method is used to verify mobile user mobile number by user
	 * provided OTP and the one that is available in the database. It changes the
	 * mobileVerificationStatus to VERIFIED and it also updates the enabled field of
	 * the users table, so that the users can login t the system.
	 * 
	 * @param mobileVerificationVo
	 * @return VerificationStatus
	 */
	public VerificationStatus verifyMobileNumber(MobileVerificationVo mobileVerificationVo) {
		VerificationCode verificationCode = verificationCodeRepository
				.findLatestCode(NotificationDeliveryType.SMS, mobileVerificationVo.getMobile()).get(0);
		if (mobileVerificationVo.getOtp().equals(verificationCode.getOtp())) {
			if (new Date().before(verificationCode.getExpirationTime())) {
				Customer customer = getIfCustomerExistsBasedOnMobile(mobileVerificationVo.getMobile());
				customer.setMobileVerificationStatus(VerificationStatus.VERIFIED);
				customerRepository.save(customer);
				User user = userRepository.findByUsername(mobileVerificationVo.getMobile());
				user.setEnabled(true);
				userRepository.save(user);
				return VerificationStatus.VERIFIED;
			} else {
				return VerificationStatus.OTP_EXPIRED;
			}
		} else {
			return VerificationStatus.NOT_VERIFIED;
		}
	}

	/**
	 * updateMobileOnSignup method updates the already existing mobile number with a
	 * new one during sign up operation by the user. Also, triggers verification
	 * code.
	 * 
	 * @param signupMobileUpdateVo
	 * @return status
	 */
	public String updateMobileOnSignup(SignupMobileUpdateVo signupMobileUpdateVo) {
		Customer existingCustomer = getIfCustomerExistsBasedOnMobile(signupMobileUpdateVo.getMobile());
		if (Objects.nonNull(existingCustomer)) {
			return UserSignupStatus.MOBILE_NUMBER_EXISTS.toString();
		}
		Customer customer = customerRepository.findById(signupMobileUpdateVo.getCustomerId()).get();
		customer.setMobile(signupMobileUpdateVo.getMobile());
		customerRepository.save(customer);
		sendMobileVerificationCode(signupMobileUpdateVo.getMobile(), NotificationUserType.CUSTOMER);
		return UpdationStatus.SUCCESS.toString();
	}

	/**
	 * resendVerificationCode method is used to re-send verification code during
	 * sign up operation by the customer.
	 * 
	 * @param customerId
	 * @return UpdationStatus
	 */
	public UpdationStatus resendVerificationCode(Long customerId) {
		Customer customer = customerRepository.findById(customerId).get();
		if (sendMobileVerificationCode(customer.getMobile(), NotificationUserType.CUSTOMER)) {
			return UpdationStatus.SUCCESS;
		}
		return UpdationStatus.FAILURE;
	}
}