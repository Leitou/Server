package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.error.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<String> handleUsernameTaken(UsernameTakenException err){
        return new ResponseEntity<>(err.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginError.class)
    public ResponseEntity<String> handleLoginError(LoginError err){
        if (err.getMessage().contains("register")){
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
