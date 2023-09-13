package org.necasond.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import powerplant.registry.service.api.DefaultApiDelegate
import powerplant.registry.service.model.Powerplant

@Service
class RegistryController : DefaultApiDelegate {

    override fun addPowerplant(powerplant: Powerplant): ResponseEntity<Powerplant> {
        println("addPowerplant")
        return ResponseEntity.ok(
            Powerplant(
                systemId = 420111777,
                plantId = "Solar-park-Prerov"
            )
        )
    }

    override fun deletePlantById(plantId: String): ResponseEntity<Unit> {
        println("deletePlantById")
        return ResponseEntity.ok(Unit)

    }

    override fun getPlantById(plantId: String): ResponseEntity<Powerplant> {
        println("getPlantById")
        return ResponseEntity.ok(
            Powerplant(
                systemId = 420111777,
                plantId = "Solar-park-Prerov"
            )
        )
    }
}
