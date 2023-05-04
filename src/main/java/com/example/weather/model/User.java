package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="city")
    private String city;
    @Column(name="country_code")
    private String countryCode;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<UserNotification> userNotification;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPreference> userPreference;


}
