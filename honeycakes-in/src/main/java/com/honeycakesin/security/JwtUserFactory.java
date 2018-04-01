package com.honeycakesin.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.honeycakesin.dto.RoleDto;
import com.honeycakesin.dto.UserDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(UserDto userDto) {
        return new JwtUser(
        		userDto.getUserId(),
        		userDto.getUsername(),
        		userDto.getPassword(),
        		mapToGrantedRoles(userDto.getRoleDtoList())
        );
    }

    private static List<GrantedAuthority> mapToGrantedRoles(List<RoleDto> roleDtoList) {
        return roleDtoList.stream()
                .map(roleDto -> new SimpleGrantedAuthority(roleDto.getUserRole().name()))
                .collect(Collectors.toList());
    }
}
