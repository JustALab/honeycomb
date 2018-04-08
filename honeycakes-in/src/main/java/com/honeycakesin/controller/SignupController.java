package com.honeycakesin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.honeycakesin.dto.SignupDto;
import com.honeycakesin.service.SignupService;
import com.honeycakesin.vo.MobileVerificationVo;

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
	
	public ResponseEntity<?> verifyMobileNumber(@Valid @RequestBody MobileVerificationVo mobileVerification){
		return null;
	}

}
