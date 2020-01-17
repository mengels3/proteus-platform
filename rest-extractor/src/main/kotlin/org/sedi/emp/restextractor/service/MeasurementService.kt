package org.sedi.emp.restextractor.service

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.model.sensordata.SensorData
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.transaction.Transactional

@Service
class MeasurementService(
        private val measurementRepository: MeasurementRepository,
        private val wellRepository: WellRepository,
        private val sensorTypeService: SensorTypeService) {

    companion object {
        private val logger = LoggerFactory.getLogger(MeasurementService::class.java)
    }

    fun findAll(): List<Measurement> {
        return measurementRepository
                .findAll()
                .toList()
    }

    @Transactional
    fun addMeasurement(sensorData: SensorData): Optional<List<Measurement>> {
        val deviceId: String = sensorData.deviceId
        val timestamp = sensorData.timestamp

        val measurements = sensorData.dataPoints
                .map { toMeasurement(it, timestamp) }
                .toList()

        return findWellAndSaveMeasurements(
                measurements = measurements,
                deviceId = deviceId
        )
    }

    @Transactional
    fun addMeasurement(measurement: Measurement, deviceId: String): Optional<List<Measurement>> {
        return findWellAndSaveMeasurements(
                measurements = listOf(measurement),
                deviceId = deviceId
        )
    }

    fun computeSensorData(rawSensorData: String): SensorData {
        val deviceID = "device_id"
        val keyValues = rawSensorData
                .splitToSequence(";")
                .map { computeKeyValue(it) }
                .toList()

        val deviceId = keyValues
                .filter { it.first == deviceID }
                .first()
                .second

        val dataPoints = keyValues
                .filter { it.first != deviceID }
                .map { Pair(computeSensorType(it.first), it.second) }
                .filter { checkSanity(it) }
                .toList()

        return SensorData(
                deviceId = deviceId,
                timestamp = Instant.now(),
                dataPoints = dataPoints
        )
    }

    private fun checkSanity(sensorTypeValuePair: Pair<SensorType, String>): Boolean {
        return when (sensorTypeValuePair.first.sensorTypeValue) {
            "ph" -> checkPhValue(sensorTypeValuePair.second)
            else -> true
        }
    }

    private fun checkPhValue(phValue: String): Boolean {
        val doublePhValue = phValue.toDoubleOrNull()
        return (doublePhValue != null) && (doublePhValue > 0) && (doublePhValue < 14)
    }

    private fun findWellAndSaveMeasurements(measurements: List<Measurement>, deviceId: String): Optional<List<Measurement>> {
        logger.debug("Looking up well for device ID $deviceId ...")
        val maybeWell = wellRepository.findByDeviceId(deviceId)

        return if (maybeWell.isPresent) {
            val well = maybeWell.get()

            logger.debug("Saving measurements:")
            measurements
                    .forEach { logger.debug(" -> $it") }

            val savedMeasurements = measurements
                    .asSequence()
                    .map { saveMeasurement(it, well.id!!) }
                    .toList()
            Optional.of(savedMeasurements)

        } else {
            logger.info("Couldn't find well for deviceId $deviceId!")
            Optional.empty<List<Measurement>>()
        }
    }

    private fun saveMeasurement(m: Measurement, wellId: UUID): Measurement {
        val savedSensorType = sensorTypeService.findOrCreate(m.sensorType)
        val m2 = m.copy(
                sensorType = savedSensorType,
                wellId = wellId
        )
        return measurementRepository.save(m2)
    }

    private fun computeKeyValue(rawKeyValue: String): Pair<String, String> {
        val splits = rawKeyValue
                .splitToSequence("=")
                .toList()
        return Pair(splits[0], splits[1])
    }

    private fun toMeasurement(sensorDataPoint: Pair<SensorType, String>, timestamp: Instant): Measurement {
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
