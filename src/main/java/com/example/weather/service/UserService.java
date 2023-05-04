package com.example.weather.service;

import com.example.weather.dto.UserPreferenceReqDto;
import com.example.weather.dto.UserPreferenceResDto;
import com.example.weather.dto.WeatherResponse;
import com.example.weather.model.User;

import java.util.List;


public interface UserService {

    User getUserById(Integer userId) throws Exception;

    List<User> getAllUserLocation() throws Exception;

    List<UserPreferenceResDto> getUserPreferenceById(Integer userId) throws Exception;

    UserPreferenceResDto updateUserPreferenceById(UserPreferenceReqDto userPreferenceReq) throws Exception;

    void updateUserLocationTemperature(WeatherResponse weatherResponse, User user) throws Exception;

    void triggerNotification();
}
