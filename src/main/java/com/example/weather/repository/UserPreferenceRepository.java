package com.example.weather.repository;

import com.example.weather.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Integer> {

   @Query(value = "SELECT n.preference_id as preferenceId FROM user_preference p JOIN user_notification n ON n.preference_id = p.id WHERE n.status =:status AND DATE(n.last_updated_date) =DATE(NOW())", nativeQuery = true)
   List<Integer> findNotifiedPreference(String status);

    @Query("SELECT p FROM UserPreference p WHERE p.id NOT IN(:preferenceIds)")
    List<UserPreference> findNonNotifiedPreferences(List<Integer> preferenceIds);
}
