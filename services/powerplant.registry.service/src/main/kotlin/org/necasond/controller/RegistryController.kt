package org.necasond.controller

import io.github.crackthecodeabhi.kreds.connection.KredsClient
import kotlinx.coroutines.runBlocking
import org.necasond.config.PowerPlantRegistryEnv
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
) : DefaultApiDelegate {

    val client: KredsClient by lazy {
        runBlocking {
            RedisClientFactory.create(
                address = env.config.redis.address,
                port = env.config.redis.port,
            )
        }
    }
    override fun addPowerplant(powerplant: Powerplant): ResponseEntity<Powerplant> {
        // Run sanity checks on the input
        if (powerplant.plantId == null || powerplant.systemId == null) {
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
            return ResponseEntity.internalServerError().build()
        }
        // Return the powerplant
        return ResponseEntity.ok(powerplant)
    }

    override fun deletePlantById(plantId: String): ResponseEntity<Unit> {
        // Run sanity checks on the input
        if (plantId.isBlank()) {
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
            return ResponseEntity.internalServerError().build()
        }
        return ResponseEntity.ok(Unit)
    }

    override fun getPlantById(plantId: String): ResponseEntity<Powerplant> {
        // Run sanity checks on the input
        if (plantId.isBlank()) {
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
            return ResponseEntity.internalServerError().build()
        }
        // Return the powerplant
        if (result == null) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(
            Powerplant(
                systemId = result!!.toLong(),
                plantId = plantId
            )
        )
    }
}
