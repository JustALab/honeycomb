package com.honeycakesin.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JwtUser implements UserDetails {

	@JsonIgnore
    Long userId;
	
	@JsonIgnore
    String password;
	
    String username;
    
    Collection<? extends GrantedAuthority> rolseDtos;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return rolseDtos;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
