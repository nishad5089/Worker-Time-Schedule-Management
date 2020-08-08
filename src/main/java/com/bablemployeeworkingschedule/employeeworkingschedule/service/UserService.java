package com.bablemployeeworkingschedule.employeeworkingschedule.service;

import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.User;
import com.bablemployeeworkingschedule.employeeworkingschedule.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    User save(UserRegistrationDto registration);
    void deleteById(long id);
}
