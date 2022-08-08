package com.victoriaKlein.UserSignUpSignIn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoUserFoundException.class)
    protected ResponseEntity<CustomException> customException() {
        return new ResponseEntity<>(new CustomException("There is no such user"), HttpStatus.NOT_FOUND);
    }
    private static class CustomException {
        private String message;
        public CustomException(String message) {
            this.message=message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
