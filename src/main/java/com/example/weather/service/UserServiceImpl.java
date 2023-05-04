package com.example.weather.service;

import com.example.weather.dto.UserLocation;
import com.example.weather.dto.UserPreferenceReqDto;
import com.example.weather.dto.UserPreferenceResDto;
import com.example.weather.dto.WeatherResponse;
import com.example.weather.exception.CustomException;
import com.example.weather.model.User;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    @Autowired
    UserLocationRepository userLocationRepository;

    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Autowired
    NotificationService notificationService;

    @Override
    public User getUserById(Integer userId) throws Exception {
        User user = null;
        try {
            user = userRepository.findByUserId(userId);
            if(null == user){
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    public List<User> getAllUserLocation() throws Exception {
        List<User> user = null;
        try {
            user = userRepository.findUserLocations();
            return user;
        } catch (Exception ex) {
            throw new CustomException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserPreferenceResDto> getUserPreferenceById(Integer userId) throws Exception {
        User user = null;
        List<UserPreference> userPreference;
        List<UserPreferenceResDto> userPreferenceRes;
        try {
            user = userRepository.findByUserId(userId);
            userPreference = user.getUserPreference();
            if(null == userPreference){
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new CustomException("User preference not found", HttpStatus.NOT_FOUND);
        }
        return userPreference.stream().map(data -> mapUserPreferenceRes(data)).collect(Collectors.toList());
    }

    public UserPreferenceResDto mapUserPreferenceRes(UserPreference userPreference){
        UserPreferenceResDto userPreferenceResDto = new UserPreferenceResDto();
        userPreferenceResDto.setPreference_id(userPreference.getId());
        userPreferenceResDto.setName(userPreference.getUser().getName());
        userPreferenceResDto.setTemperature(userPreference.getTemperature());
        return userPreferenceResDto;
    }

    @Override
    public UserPreferenceResDto updateUserPreferenceById(UserPreferenceReqDto userPreferenceReq) throws Exception {
        User user = null;
        UserPreference userPreference = null;
        UserPreferenceResDto userPreferenceRes =  new UserPreferenceResDto();
        try {
            user = userRepository.findByPreferenceId(userPreferenceReq.getPreference_id());
            userPreference = user.getUserPreference().get(0);
            if(null == userPreference){
                throw new Exception();
            }
            userPreference.setTemperature(userPreferenceReq.getTemperature());
            userPreferenceRepository.save(userPreference);
            userPreferenceRes.setPreference_id(userPreferenceReq.getPreference_id());
            userPreferenceRes.setTemperature(userPreferenceReq.getTemperature());
            userPreferenceRes.setName(user.getName());
        }catch (Exception ex) {
            throw new CustomException("User preference not found", HttpStatus.NOT_FOUND);
        }
        return userPreferenceRes;
    }

    @Override
    public void updateUserLocationTemperature(WeatherResponse weatherResponse, User user) throws Exception {
        UserLocationTemperature userLocationTemperature = null;
        try {
            userLocationTemperature = userLocationRepository.findByCityAndCountryCode(user.getCity(), user.getCountryCode());
            if(null != userLocationTemperature) {
                userLocationTemperature.setCity(user.getCity());
                userLocationTemperature.setCountryCode(user.getCountryCode());
                userLocationTemperature.setCurrentTemperature(weatherResponse.getMain().getTemp());
                userLocationTemperature.setDescription(weatherResponse.getWeather().get(0).getDescription());
            } else {
                userLocationTemperature = new UserLocationTemperature();
                userLocationTemperature.setId(null);
                userLocationTemperature.setCity(user.getCity());
                userLocationTemperature.setCountryCode(user.getCountryCode());
                userLocationTemperature.setCurrentTemperature(weatherResponse.getMain().getTemp());
                userLocationTemperature.setDescription(weatherResponse.getWeather().get(0).getDescription());
            }
            userLocationRepository.save(userLocationTemperature);
        } catch (Exception ex) {
            throw new CustomException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void triggerNotification() {

        List<Integer> preferenceIds = userPreferenceRepository.findNotifiedPreference("SENT");

        List<UserPreference> preferences = userPreferenceRepository.findNonNotifiedPreferences(preferenceIds);

        preferences.stream().forEach(data -> notificationService.compareAndTrigger(data));

    }



}
