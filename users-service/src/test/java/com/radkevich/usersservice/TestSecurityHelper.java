package com.radkevich.usersservice;

import com.radkevich.usersservice.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;

public class TestSecurityHelper {
    public static Authentication getTestAuthentication(Integer userId) {
        CustomUserDetails userDetails = new CustomUserDetails("test-user", "test-pass", true,
                true, true, true,
                AuthorityUtils.createAuthorityList("user"),
                userId);

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                new Jwt("abc", Instant.now(), Instant.MAX,
                        Map.of("header", "headerval"),
                        Map.of("claim", "claimval")),
                userDetails.getAuthorities());
        jwtAuthenticationToken.setDetails(userDetails);
        return jwtAuthenticationToken;
    }
}