package org.necasond

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import powerplant.registry.service.invoker.Application


@SpringBootApplication(scanBasePackages = ["org.necasond", "powerplant.registry.service.api"])
class PowerplantRegistryApplication

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<PowerplantRegistryApplication>(*args)
    }
}

//@Bean
//fun openAPI(): OpenAPI {
//    return OpenAPI().info(
//        Info().title("SpringDoc example")
//            .description("SpringDoc application")
//            .version("v0.0.1")
//    )
//}
