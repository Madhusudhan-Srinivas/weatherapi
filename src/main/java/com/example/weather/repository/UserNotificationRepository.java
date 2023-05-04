package com.example.weather.repository;

import com.example.weather.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {

    @Query("SELECT n FROM UserNotification n JOIN n.userPreference p WHERE p.id=:preferenceId")
    UserNotification findByPreferenceId(Integer preferenceId);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO user_notification VALUES (null, :preferenceId, 'PENDING', now(), :message)", nativeQuery = true)
    void saveNewNotification(Integer preferenceId, String message);
}
