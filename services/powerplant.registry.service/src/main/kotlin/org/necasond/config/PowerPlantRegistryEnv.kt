package org.necasond.config


import org.springframework.stereotype.Service

@Service
class PowerPlantRegistryEnv {
    val config: PowerPlantRegistryConfig = PowerPlantRegistryConfig()
}
