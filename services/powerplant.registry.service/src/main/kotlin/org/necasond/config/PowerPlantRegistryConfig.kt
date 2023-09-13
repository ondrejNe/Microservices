package org.necasond.config

import org.necasond.redis.RedisEnv

data class PowerPlantRegistryConfig (
    val redis: RedisEnv = RedisEnv(),
)
