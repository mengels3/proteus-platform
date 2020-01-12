package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.service.MeasurementService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MeasurementsController(private val measurementService: MeasurementService) {

    companion object {
        private val logger = LoggerFactory.getLogger(MeasurementsController::class.java)
    }

    @GetMapping("/measurement")
    fun getMeasurements(): List<Measurement> {
        val measurements = measurementService.findAll()
        logger.debug("Returning ${measurements.size} measurement(s)")
        return measurements
    }

    @PostMapping("/publish")
    fun publishData(@RequestBody body: MultiValueMap<String, String>): ResponseEntity<List<Measurement>> {
        val rawSensorData: String = body["data"]!!.first()
        logger.debug("Received data: $rawSensorData")
        val sensorData = measurementService.computeSensorData(rawSensorData)
        return ResponseEntity.of(measurementService.addMeasurement(sensorData))
    }
}
