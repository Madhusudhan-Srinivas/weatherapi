package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="user_location_temperature")
@Data
public class UserLocationTemperature {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="current_temperature")
    private Double currentTemperature;
    @Column(name="description")
    private String description;
    @Column(name="city")
    private String city;
    @Column(name="country_code")
    private String countryCode;
}
