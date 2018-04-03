package com.honeycakesin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ramu Ramasamy
 *
 */
@Component
public class CustomJwtTokenUtil {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public String getUsernameFromToken(String authorizationHeader) {
		return jwtTokenUtil.getUsernameFromToken(returnSubstring(authorizationHeader));
	}

	public String returnSubstring(String authorizationHeader) {
		return authorizationHeader.substring(7);
	}

}
