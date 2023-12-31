package org.necasond.config

import org.necasond.logging.ILoggable
import org.necasond.logging.logger
import org.necasond.redis.RedisEnv

data class WeatherConfig (
    val redis: RedisEnv = RedisEnv(),
) {
    companion object : ILoggable {
        private val logger = logger()
    }

    fun load(): WeatherConfig {
        val systemEnv = System.getenv()
        logger.info("System environment: $systemEnv")

        val redisEnv = RedisEnv(
            address = systemEnv["redis.address"] ?: redis.address,
            port = systemEnv["redis.port"] ?: redis.port,
            pass = systemEnv["redis.pass"] ?: redis.pass,
        )
        logger.info("Loaded redis environment: $redisEnv")

        return WeatherConfig(
            redis = redisEnv,
        )
    }
}