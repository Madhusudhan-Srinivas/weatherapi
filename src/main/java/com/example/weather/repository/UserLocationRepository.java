package com.example.weather.repository;

import com.example.weather.model.UserLocationTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocationTemperature, Integer> {


    @Query("SELECT t FROM UserLocationTemperature t WHERE t.city =:city AND t.countryCode =:countryCode")
    UserLocationTemperature findByCityAndCountryCode(String city, String countryCode);

    @Query("SELECT t FROM UserLocationTemperature t WHERE t.currentTemperature =:preferenceTemperature AND t.city =:city AND t.countryCode =:countryCode")
    UserLocationTemperature compareUserPreferenceTemperature(Double preferenceTemperature, String city, String countryCode);
}
