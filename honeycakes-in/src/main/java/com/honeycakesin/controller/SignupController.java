package com.honeycakesin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.honeycakesin.dto.SignupDto;
import com.honeycakesin.service.SignupService;
import com.honeycakesin.vo.MobileVerificationVo;
import com.honeycakesin.vo.SignupMobileUpdateVo;

@RestController
@RequestMapping("/hc/signup")
public class SignupController {

	@Autowired
	SignupService signupService;

	/**
	 * addNewCustomer method is used to add a new customer. This method also adds a
	 * user to the application.
	 * 
	 * @param signupDto
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public ResponseEntity<?> addNewCustomer(@Valid @RequestBody SignupDto signupDto) {
		return ResponseEntity.ok(signupService.addNewCustomer(signupDto));
	}

	/**
	 * verifyMobileNumber method is used to verify mobile number with the
	 * verification code that has been sent to the user.Ï
	 * 
	 * @param mobileVerificationVo
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/verifyMobile", method = RequestMethod.PUT)
	public ResponseEntity<?> verifyMobileNumber(@Valid @RequestBody MobileVerificationVo mobileVerificationVo) {
		return ResponseEntity.ok(signupService.verifyMobileNumber(mobileVerificationVo));
	}

	/**
	 * updateMobileOnSignup method is used to update the mobile number during sign
	 * up operation by the customer.
	 * 
	 * @param signupMobileUpdateVo
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/updateMobileOnSignup", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMobileOnSignup(@Valid @RequestBody SignupMobileUpdateVo signupMobileUpdateVo) {
		return ResponseEntity.ok(signupService.updateMobileOnSignup(signupMobileUpdateVo));
	}

	/**
	 * resendVerificationCode method is used to re-send verification code during
	 * sign up operation by the customer.
	 * 
	 * @param customerId
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/resendVerificationCode/{customerId}", method = RequestMethod.POST)
	public ResponseEntity<?> resendVerificationCode(@PathVariable("customerId") Long customerId) {
		return ResponseEntity.ok(signupService.resendVerificationCode(customerId));
	}

}
