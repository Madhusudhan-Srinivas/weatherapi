package com.example.weather.service;

import com.example.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.weather.api.key}")
    private String apiKey;

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={location}&APPID={key}&units=metric";

    @Override
    public WeatherResponse getWeather(String location) {
        URI url = new UriTemplate(WEATHER_URL).expand(location, this.apiKey);
        return invoke(url, WeatherResponse.class);
    }

    private <T> T invoke(URI url, Class<T> responseType) {;
        RequestEntity<?> request = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }
}
