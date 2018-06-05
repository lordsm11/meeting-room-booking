package com.devakt.service;

import com.devakt.exception.LoginException;
import com.devakt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public void login(String email, String password) {
        userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(LoginException::new);
    }
}
