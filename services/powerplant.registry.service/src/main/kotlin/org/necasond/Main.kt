package org.necasond

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.necasond", "powerplant.registry.service.api"])
class PowerplantRegistryApplication

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<PowerplantRegistryApplication>(*args)
    }
}
