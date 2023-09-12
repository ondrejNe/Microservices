package org.necasond.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class WeatherController {
    @GetMapping("/")
    fun index(): String {
        return "Greetings from Spring Boot!"
    }

    @GetMapping("/current/weather")
    fun currentWeather(): String {
        return "Currently is sunny in Philadelphia"
    }
}
