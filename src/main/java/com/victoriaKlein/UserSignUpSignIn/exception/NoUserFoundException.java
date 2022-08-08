package com.victoriaKlein.UserSignUpSignIn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No user found")
public class NoUserFoundException extends RuntimeException{
}
