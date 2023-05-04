package com.example.weather.exception;

import com.example.weather.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex, WebRequest request){
        return new ResponseEntity(new ErrorDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex, WebRequest request){
        return new ResponseEntity(new ErrorDto(new Date(), ex.getStatus(), ex.getLocalizedMessage(), request.getDescription(false)), ex.getStatus());
    }

}
