package com.example.weather.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException{

    protected HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status =status;
    }


}
