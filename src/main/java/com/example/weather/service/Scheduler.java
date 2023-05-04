package com.example.weather.service;

import com.example.weather.dto.WeatherResponse;
import com.example.weather.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    @Autowired
    UserService userService;

    @Autowired
    WeatherService weatherService;

    @Scheduled(fixedDelay = 120000)
    public void getUserLocationCurrentTemperature() throws Exception {

        List<User> users = userService.getAllUserLocation();

        users.stream().forEach(user ->updateUserLocationTemperature(weatherService.getWeather(mapUserLocation(user)), user));

    }

    public String mapUserLocation(User user){
        return user.getCity()+","+user.getCountryCode();
    }

    public void updateUserLocationTemperature(WeatherResponse weatherResponse, User user){

        System.out.println(user.getCity()+","+user.getCountryCode());
        System.out.println(weatherResponse);

        try {
            userService.updateUserLocationTemperature(weatherResponse,user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(fixedDelay = 120000)
    public void triggerNotification(){

        userService.triggerNotification();

    }

}
