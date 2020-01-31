package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.persistence.SensorTypeRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SensorTypeController(private val sensorTypeRepository: SensorTypeRepository) {

    private val logger = LoggerFactory.getLogger(SensorTypeController::class.java)

    @GetMapping("/sensor-type")
    fun allSensorTypes(): List<SensorType> {
        val sensorTypes = sensorTypeRepository
                .findAll()
                .toList()

        logger.debug("Returning ${sensorTypes.size} sensor types")
        return sensorTypes
    }
}
