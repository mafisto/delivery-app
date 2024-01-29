package com.radkevich.usersservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final UserDetailsService userDetailsService;

    public AbstractAuthenticationToken addRoles(Jwt jwt) {
        String sub = jwt.getClaimAsString(JwtClaimNames.SUB);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(sub);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, userDetails.getAuthorities());
        jwtAuthenticationToken.setDetails(userDetails);
        return jwtAuthenticationToken;
    }
}
