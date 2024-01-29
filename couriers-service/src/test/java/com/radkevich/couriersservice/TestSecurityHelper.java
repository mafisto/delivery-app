package com.radkevich.couriersservice;

import com.radkevich.couriersservice.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;

public class TestSecurityHelper {
    public static Authentication getTestAuthentication(Integer userId) {
        CustomUserDetails userDetails = new CustomUserDetails("test-corier", "test-pass", true,
                true, true, true,
                AuthorityUtils.createAuthorityList("courier"),
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