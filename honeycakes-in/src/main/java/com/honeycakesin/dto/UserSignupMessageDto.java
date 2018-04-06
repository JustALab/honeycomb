package com.honeycakesin.dto;

import java.io.Serializable;

import com.honeycakesin.constants.UserSignupStatus;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserSignupMessageDto implements Serializable{
	
	UserSignupStatus signupStatus;
	
	String message;
	
}
