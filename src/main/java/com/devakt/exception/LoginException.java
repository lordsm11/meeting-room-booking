package com.devakt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginException() {
        super(String.format("Impossible de se connecter, merci de vérifier vos paramètres"));
    }

}
