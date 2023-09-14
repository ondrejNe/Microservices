package org.necasond.config

import org.springframework.stereotype.Service

@Service
class WeatherEnv {
    val config: WeatherConfig
    = WeatherConfig().load()
}
