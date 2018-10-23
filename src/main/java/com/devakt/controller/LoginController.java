package com.devakt.controller;

import com.devakt.service.LoginService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"}, maxAge = 3600)
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity book(@RequestBody LoginView loginView) {
        loginService.login(loginView.getEmail(), loginView.getPassword());
        return ResponseEntity.noContent().build();
    }

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    static class LoginView {
        String email;
        String password;
    }

}












