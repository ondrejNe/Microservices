package org.necasond

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClientConfig
import io.github.crackthecodeabhi.kreds.connection.newClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
