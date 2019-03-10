package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.error.AccessDeniedException;
import ch.uzh.ifi.seal.soprafs19.error.LoginError;
import ch.uzh.ifi.seal.soprafs19.error.RegisterError;
import ch.uzh.ifi.seal.soprafs19.error.UserNonexistentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(RegisterError.class)
    public ResponseEntity<String> handleRegisterError(RegisterError err){
        return new ResponseEntity<>(err.getMessage(),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(LoginError.class)
    public ResponseEntity<String> handleLoginError(LoginError err){
        if (err.getMessage().contains("register first")){
            return new ResponseEntity<>(err.getMessage(),HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(err.getMessage(),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNonexistentException.class)
    public ResponseEntity<String> handleUserNonexistent(){
        return new ResponseEntity<>("User Id does not exist",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenial(){
        return new ResponseEntity<>("Access denied, wrong token",HttpStatus.FORBIDDEN);
    }
}
