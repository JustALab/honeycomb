package com.honeycakesin.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.honeycakesin.constants.Gender;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class SignupDto implements Serializable {

	@NotNull
	String firstName;

	@NotNull
	String lastName;
	
	@NotNull
	Gender gender;

	@NotNull
	String email;

	@NotNull
	String mobile;

	@NotNull
	String password;

}
