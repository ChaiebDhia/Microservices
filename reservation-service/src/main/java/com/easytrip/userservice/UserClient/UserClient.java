package com.easytrip.userservice.UserClient;

import com.easytrip.userservice.dto.UserResponse;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Nom du microservice tel qu’il est défini dans Eureka
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}