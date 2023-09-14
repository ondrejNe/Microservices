package org.necasond

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.necasond", "weather.service"])
class WeatherApplication

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<WeatherApplication>(*args)
    }
}
