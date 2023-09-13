package org.necasond.controller

import io.github.crackthecodeabhi.kreds.connection.KredsClient
import kotlinx.coroutines.runBlocking
import org.necasond.config.PowerPlantRegistryEnv
import org.necasond.logging.ILoggable
import org.necasond.logging.logger
import org.necasond.redis.RedisClientFactory
import org.necasond.redis.RedisClientUtil.delete
import org.necasond.redis.RedisClientUtil.retrieve
import org.necasond.redis.RedisClientUtil.upload
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import powerplant.registry.service.api.DefaultApiDelegate
import powerplant.registry.service.model.Powerplant

@Component
class RegistryController(
    private val env: PowerPlantRegistryEnv
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
    override fun addPowerplant(powerplant: Powerplant): ResponseEntity<Powerplant> {
        logger.info("Received add powerplant request: $powerplant")
        // Run sanity checks on the input
        if (powerplant.plantId == null || powerplant.systemId == null) {
            logger.warn("Received invalid powerplant request: $powerplant")
            return ResponseEntity.badRequest().build()
        }
        // Upload the powerplant to redis
        runCatching {
            runBlocking {
                client.upload(
                    key = powerplant.plantId,
                    value = powerplant.systemId.toString(),
                    pass = env.config.redis.pass,
                )
            }
        }.onFailure {
            logger.error("Failed to upload powerplant to redis: $powerplant", it)
            return ResponseEntity.internalServerError().build()
        }
        // Return the powerplant
        logger.info("Successfully added powerplant: $powerplant")
        return ResponseEntity.ok(powerplant)
    }

    override fun deletePlantById(plantId: String): ResponseEntity<Unit> {
        logger.info("Received delete powerplant request: $plantId")
        // Run sanity checks on the input
        if (plantId.isBlank()) {
            logger.warn("Received invalid powerplant request: $plantId")
            return ResponseEntity.badRequest().build()
        }
        // Delete the powerplant from redis
        runCatching {
            runBlocking {
                client.delete(
                    key = plantId,
                    pass = env.config.redis.pass,
                )
            }
        }.onFailure {
            logger.error("Failed to delete powerplant from redis: $plantId", it)
            return ResponseEntity.internalServerError().build()
        }
        logger.info("Successfully deleted powerplant: $plantId")
        return ResponseEntity.ok(Unit)
    }

    override fun getPlantById(plantId: String): ResponseEntity<Powerplant> {
        logger.info("Received get powerplant request: $plantId")
        // Run sanity checks on the input
        if (plantId.isBlank()) {
            logger.warn("Received invalid powerplant request: $plantId")
            return ResponseEntity.badRequest().build()
        }
        // Retrieve the powerplant from redis
        var result: String? = null
        runCatching {
            runBlocking {
                result = client.retrieve(
                    key = plantId,
                    pass = env.config.redis.pass,
                )
            }
        }.onFailure {
            logger.error("Failed to retrieve powerplant from redis: $plantId", it)
            return ResponseEntity.internalServerError().build()
        }
        // Return the powerplant
        if (result == null) {
            logger.warn("Failed to retrieve powerplant from redis: $plantId")
            return ResponseEntity.notFound().build()
        }
        logger.info("Successfully retrieved powerplant: $plantId")
        return ResponseEntity.ok(
            Powerplant(
                systemId = result!!.toLong(),
                plantId = plantId
            )
        )
    }
}
