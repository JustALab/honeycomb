package com.honeycakesin.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.honeycakesin.constants.SmsOrEmailStatus;
import com.honeycakesin.constants.UserSignupStatus;
import com.honeycakesin.constants.VerificationStatus;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserSignupMessageDto implements Serializable{
	
	@Null
	Long customerId;
	
	@NotNull
	UserSignupStatus signupStatus;
	
	@NotNull
	String message;
	
	@Null
	String email;
	
	@Null
	VerificationStatus emailVerificationStatus;

	@Null
	String mobile;
	
	@Null
	VerificationStatus mobileVerificationStatus;
	
	@Null
	SmsOrEmailStatus otpStatus;
	
	@Null
	SmsOrEmailStatus verificationEmailStatus;
}
