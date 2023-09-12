package org.necasond

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PowerplantRegistryApplication

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<PowerplantRegistryApplication>(*args)
    }
}
