package org.necasond.controller

import io.github.crackthecodeabhi.kreds.connection.KredsClient
import kotlinx.coroutines.runBlocking
import org.necasond.config.WeatherEnv
import org.necasond.logging.ILoggable
import org.necasond.logging.logger
import org.necasond.model.Forecast
import org.necasond.model.ForecastResolver
import org.necasond.redis.RedisClientFactory
import org.necasond.redis.RedisClientUtil.getAll
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import weather.service.api.DefaultApiDelegate


@Controller
class WeatherController(
    private val env: WeatherEnv
) : DefaultApiDelegate, ILoggable {

    private val logger = logger()

    val client: KredsClient by lazy {
        runBlocking {
            RedisClientFactory.create(
                address = env.config.redis.address,
                port = env.config.redis.port,
            )
        }
    }

    @GetMapping
    @RequestMapping("/forecast/table", produces = [MediaType.TEXT_HTML_VALUE])
    fun getForecastTable(): ModelAndView {
        logger.info("Received get forecast table request")
        // Upload the powerplant to redis
        var result: Map<String, String> = emptyMap()
        runCatching {
            runBlocking {
                result = client.getAll(
                    pass = env.config.redis.pass,
                )
            }
        }.onFailure {
            logger.error("Failed to retrieve forecast table from redis", it)
            val model = mapOf("message" to "Failed to retrieve forecast table from redis")
            return ModelAndView("error500", model)
        }

        val forecasts = result
            .mapValues {
                ForecastResolver.resolve(it.value)
            }
            .map {
            Forecast(
                plantId = it.key,
                forecast = it.value
            )
        }

        logger.info("Successfully retrieved forecast table")
        return ModelAndView("forecast").apply {
            addObject("forecasts", forecasts)
        }
    }
}
