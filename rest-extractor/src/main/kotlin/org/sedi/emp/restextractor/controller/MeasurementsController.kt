package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MeasurementsController(private val measurementRepository: MeasurementRepository) {

    private val logger = LoggerFactory.getLogger(MeasurementsController::class.java)

    @GetMapping("/measurement")
    fun getMeasurements(): List<Measurement> {
        val measurements: List<Measurement> = measurementRepository
                .findAll()
                .toList()

        logger.debug("Returning ${measurements.size} measurement(s)")
        return measurements
    }
}
