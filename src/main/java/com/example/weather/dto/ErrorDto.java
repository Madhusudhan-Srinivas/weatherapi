package com.example.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDto {

    private Date timestamp;
    private HttpStatus status;
    private String error;
    private String path;
}
