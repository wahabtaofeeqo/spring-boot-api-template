package com.example.template.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.template.responses.ErrResponse;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        
        return buildResponse("Validation Error", status.value(), errors);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleAccessDeniedException(Exception ex, WebRequest request) {
        ErrResponse response = new ErrResponse("Access denied", ex.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AccountExistException.class})
    public ResponseEntity<?> handleAccountExistException(AccountExistException e) {
        ErrResponse error = new ErrResponse("Account already exist", e.getEmail());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex, WebRequest request) {
        return buildResponse("Operation not succeeded", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(String msg, int status, Object error) {
        ErrResponse response = new ErrResponse(msg, error);
        return ResponseEntity.status(status).body(response);
    }
}
