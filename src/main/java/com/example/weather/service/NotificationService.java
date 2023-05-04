package com.example.weather.service;

import com.example.weather.model.UserPreference;

public interface NotificationService {

    void compareAndTrigger(UserPreference userPreference);
}
