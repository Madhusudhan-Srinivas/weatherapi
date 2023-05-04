package com.example.weather.service;

import com.example.weather.dto.WeatherResponse;

public interface WeatherService {

    WeatherResponse getWeather(String location);
}
