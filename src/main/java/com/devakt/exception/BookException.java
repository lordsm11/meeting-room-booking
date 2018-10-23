package com.devakt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BookException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BookException() {
        super(String.format("Une erreur est survenue lors de la réservation, la salle n'est plus disponible à l'horaire choisi"));
    }

}
