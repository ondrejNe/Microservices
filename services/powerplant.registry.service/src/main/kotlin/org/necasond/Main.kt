package org.necasond

import org.necasond.logging.ILoggable
import org.necasond.logging.logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.necasond", "powerplant.registry.service"])
class PowerplantRegistryApplication

object Main : ILoggable {
    private val logger = logger()

    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Starting powerplant registry service")
        runApplication<PowerplantRegistryApplication>(*args)
    }
}
