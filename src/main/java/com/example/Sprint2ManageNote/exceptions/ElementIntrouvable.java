package com.example.Sprint2ManageNote.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementIntrouvable extends RuntimeException {
    public ElementIntrouvable(String s, HttpStatus notFound) {
        super(s);
    }
}
