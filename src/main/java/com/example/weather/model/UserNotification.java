package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_notification")
@Data
public class UserNotification {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="status")
    private String status;
    @Column(name="last_updated_date")
    private Date lastUpdatedDate;
    @Column(name="message")
    private String message;

//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "preference_id", nullable = false)
    private UserPreference userPreference;
}
