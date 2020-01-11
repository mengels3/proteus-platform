package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.model.sensordata.SensorData
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.format.DateTimeFormatter

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
//    @Transactional
    fun publishData(@RequestBody body: MultiValueMap<String, String>): ResponseEntity<String> {
        val rawSensorData: String = body["data"]!!.first()
        logger.debug("Received data: $rawSensorData")
        val sensorData: SensorData = computeSensorData(rawSensorData)

        val maybeWell = wellRepository.findByDeviceId(sensorData.deviceId)
        return if (maybeWell.isPresent) {
            val well = maybeWell.get()

            val timestamp = sensorData.timestamp
            val measurements = sensorData.dataPoints
                    .map { toMeasurement(it, timestamp) }
                    .toList()

            logger.debug("Saving well $well with measurements:")
            measurements
                    .forEach { logger.debug(" -> $it") }

            measurements
                    .forEach { well.measurements += it }
//            wellRepository.save(well)
            ResponseEntity.ok("true")

        } else {
            logger.info("Couldn't find well for deviceId $sensorData.deviceId!")
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    private fun computeSensorData(rawSensorData: String): SensorData {
        val DEVICE_ID = "device_id"
        val keyValues = rawSensorData
                .splitToSequence(";")
                .map { computeKeyValue(it) }
                .toList()

        val deviceId = keyValues
                .filter { it.first == DEVICE_ID }
                .first()
                .second

        val dataPoints = keyValues
                .filter { it.first != DEVICE_ID }
                .map { Pair(computeSensorType(it.first), it.second) }
                .toList()

        val timestamp = DateTimeFormatter
                .ISO_INSTANT
                .format(Instant.now())

        return SensorData(
                deviceId = deviceId,
                timestamp = timestamp,
                dataPoints = dataPoints
        )
    }

    private fun computeKeyValue(rawKeyValue: String): Pair<String, String> {
        val splits = rawKeyValue
                .splitToSequence("=")
                .toList()
        return Pair(splits[0], splits[1])
    }

    private fun toMeasurement(sensorDataPoint: Pair<SensorType, String>, timestamp: String): Measurement {
        return Measurement(
                timestamp = timestamp,
                value = sensorDataPoint.second,
                sensorType = sensorDataPoint.first
        )
    }

    private fun computeSensorType(rawSensorType: String): SensorType {
        return SensorType(sensorTypeValue = rawSensorType)
    }
}
