package com.example.weather.dto;

import lombok.Data;

@Data
public class UserPreferenceResDto {

    private Integer preference_id;
    private String name;
    private Double temperature;
}
