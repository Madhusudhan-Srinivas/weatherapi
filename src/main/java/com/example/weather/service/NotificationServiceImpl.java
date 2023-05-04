package com.example.weather.service;

import com.example.weather.dto.UserLocation;
import com.example.weather.exception.CustomException;
import com.example.weather.model.UserLocationTemperature;
import com.example.weather.model.UserNotification;
import com.example.weather.model.UserPreference;
import com.example.weather.repository.UserLocationRepository;
import com.example.weather.repository.UserNotificationRepository;
import com.example.weather.repository.UserPreferenceRepository;
import com.example.weather.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    @Autowired
    UserLocationRepository userLocationRepository;

    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Override
    public void compareAndTrigger(UserPreference userPreference){
        UserLocation user = null;
        UserNotification userNotification = null;
        try {
            user = userRepository.findByUserIdNative(userPreference.getUser().getId());

            UserLocationTemperature userLocationTemperature = userLocationRepository.compareUserPreferenceTemperature(31.08D, user.getCity(), user.getCountryCode());

            if(null != userLocationTemperature) {
                userNotification = userNotificationRepository.findByPreferenceId(userPreference.getId());

                if(null != userNotification){
                    userNotification.setMessage(userLocationTemperature.getDescription());
                    userNotification.setStatus("PENDING");
                    userNotificationRepository.save(userNotification);
                } else {
                    userNotificationRepository.saveNewNotification(userPreference.getId(), userLocationTemperature.getDescription());
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            throw new CustomException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
