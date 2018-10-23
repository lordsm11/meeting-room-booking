package com.devakt.service;

import com.devakt.exception.LoginException;
import com.devakt.repository.UserRepository;
import com.devakt.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public void login(String email, String password) {
        userRepository.findByEmail(email)
                .filter(user -> StringUtils.equals(user.getPassword(), password))
                .orElseThrow(LoginException::new);
    }
}
