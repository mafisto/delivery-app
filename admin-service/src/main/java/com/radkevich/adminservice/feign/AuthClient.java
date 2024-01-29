package com.radkevich.adminservice.feign;

import com.radkevich.adminservice.dto.JwtRequestDto;
import com.radkevich.adminservice.dto.JwtResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authorization", url = "${auth-server.url}")
public interface AuthClient {
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    JwtResponseDto getToken(@RequestBody JwtRequestDto body);

}
