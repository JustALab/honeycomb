package com.honeycakesin.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class MobileVerificationVo implements Serializable {

	@NotNull
	String email;

	@NotNull
	String mobile;

	@NotNull
	String otp;

}
