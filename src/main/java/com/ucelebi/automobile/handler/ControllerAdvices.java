package com.ucelebi.automobile.handler;

import com.ucelebi.automobile.exception.AlreadyExistException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvices {

    public static Logger log = Logger.getLogger(ControllerAdvices.class);

    @ExceptionHandler(value = { AlreadyExistException.class })
    public ResponseEntity<Object> alreadyExistException(AlreadyExistException ex) {
        log.error("Kullanıcı sistemde mevcut.");

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<Object> failLoginRequest(BadCredentialsException ex) {
        log.error("Kullanıcı girisi basarisiz. Bilgileri kontrol ediniz.");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<List<String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorList = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    public ResponseEntity<String> dataIntegrityViolationException() {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = { SQLIntegrityConstraintViolationException.class ,NullPointerException.class})
    public ResponseEntity<Object> aestheticAppException(SQLIntegrityConstraintViolationException ex) {
        log.error("Gerekli tum bilgiler girilmemis olabilir.");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = { IOException.class})
    public ResponseEntity<Object> ioException(IOException ex) {
        log.error("Error IOException. {}", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
