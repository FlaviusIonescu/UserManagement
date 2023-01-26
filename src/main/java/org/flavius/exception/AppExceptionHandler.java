package org.flavius.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({PasswordMismatchException.class})
    public ResponseEntity<Object> passwordMismatchError(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body("New password does not match.");
    }

    @ExceptionHandler({WrongCredentialsException.class})
    public ResponseEntity<Object> credentialsError(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Username or password invalid.");
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> userNotFoundError(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User [" + ((UserNotFoundException) ex).getUsername() + "] not found.");
    }

}
