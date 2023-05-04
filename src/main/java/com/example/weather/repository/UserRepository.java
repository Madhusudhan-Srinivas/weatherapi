package com.example.weather.repository;

import com.example.weather.dto.UserLocation;
import com.example.weather.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(Integer userId);

    @Query("SELECT u FROM User u JOIN u.userPreference p WHERE p.id = :UserPreference")
    User findByPreferenceId(Integer UserPreference);

    @Query("SELECT DISTINCT u FROM User u")
    List<User> findUserLocations();

    @Query(value ="SELECT u.city as city, u.country_code as countryCode FROM user u WHERE u.id = :userId", nativeQuery = true)
    UserLocation findByUserIdNative(Integer userId);
}
