package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.model.sensordata.SensorData
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MeasurementsController(
        private val measurementRepository: MeasurementRepository,
        private val wellRepository: WellRepository) {

    private val logger = LoggerFactory.getLogger(MeasurementsController::class.java)

    @GetMapping("/measurement")
    fun getMeasurements(): List<Measurement> {
        val measurements: List<Measurement> = measurementRepository
                .findAll()
                .toList()

        logger.debug("Returning ${measurements.size} measurement(s)")
        return measurements
    }

    @PostMapping("/publish")
    fun publishSediData(@RequestBody sensorData: SensorData): ResponseEntity<String> {
        logger.debug("Received data $sensorData")
        val maybeWell = wellRepository.findByDeviceId(sensorData.deviceId)

        return if (maybeWell.isPresent) {
            val well = maybeWell.get()

            val timestamp = sensorData.timestamp
            sensorData.dataPoints
                    .map { toMeasurement(it, timestamp) }
                    .forEach { well.measurements += it }

            wellRepository.save(well)
            ResponseEntity.ok("successfully processed")

        } else {
            logger.info("Couldn't find well for deviceId $sensorData.deviceId!")
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    private fun toMeasurement(sensorDataPoint: Pair<String, String>, timestamp: String): Measurement {
        val sensorType: SensorType = computeSensorType(sensorDataPoint.first)
        return Measurement(
                timestamp = timestamp,
                value = sensorDataPoint.second,
                sensorType = sensorType
        )
    }

    private fun computeSensorType(rawSensorType: String): SensorType {
        TODO("not implemented")
    }
}
