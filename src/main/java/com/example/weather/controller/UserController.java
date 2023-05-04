package com.example.weather.controller;

import com.example.weather.dto.UserPreferenceReqDto;
import com.example.weather.dto.UserPreferenceResDto;
import com.example.weather.model.User;
import com.example.weather.model.UserPreference;
import com.example.weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path="/v1/findBy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUserById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(path="/v1/preference/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserPreferenceResDto>> findUserPreferenceById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(userService.getUserPreferenceById(id));
    }

    @PostMapping(path="/v1/preference/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPreferenceResDto> updateUserPreferenceById(@RequestBody UserPreferenceReqDto userPreference) throws Exception {
        return ResponseEntity.ok(userService.updateUserPreferenceById(userPreference));
    }
}
