package com.honeycakesin.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class SignupMobileUpdateVo implements Serializable{
	
	@NotNull
	Long customerId;
	
	@NotNull
	String mobile;
	
}
