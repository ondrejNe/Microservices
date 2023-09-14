package org.necasond.config

import org.necasond.logging.ILoggable
import org.necasond.logging.logger
import org.necasond.redis.RedisEnv

data class PowerPlantRegistryConfig (
    val redis: RedisEnv = RedisEnv(),
    val envProvider: EnvProvider = DefaultEnvProvider,
) {
    companion object : ILoggable {
        private val logger = logger()
    }

    fun load(): PowerPlantRegistryConfig {
        val systemEnv = envProvider.getEnv()
        logger.info("System environment: $systemEnv")

        val redisEnv = RedisEnv(
            address = systemEnv["redis.address"] ?: redis.address,
            port = systemEnv["redis.port"] ?: redis.port,
            pass = systemEnv["redis.pass"] ?: redis.pass,
        )
        logger.info("Loaded redis environment: $redisEnv")

        return PowerPlantRegistryConfig(
            redis = redisEnv,
        )
    }
}

interface EnvProvider {
    fun getEnv(): Map<String, String>
}

object DefaultEnvProvider : EnvProvider {
    override fun getEnv(): Map<String, String> = System.getenv()
}
