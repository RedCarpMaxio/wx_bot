package com.wxbot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/weather")
@RestController
public class WeatherController {
    @RequestMapping("/get")
    public String getWeather(@RequestParam(name = "city", defaultValue = "beijing") String city) {

        return "weather is good in " + city;
    }
}
