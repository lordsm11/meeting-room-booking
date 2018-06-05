package com.devakt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RoomNotFoundException() {
        super(String.format("La salle demandée est introuvable"));
    }

}
