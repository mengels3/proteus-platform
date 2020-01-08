package org.sedi.emp.restextractor

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.WellRepository
import java.math.BigDecimal
import java.time.Instant
import java.time.format.DateTimeFormatter

open class DatabaseInitializer(private val wellRepository: WellRepository) {

    fun initializeTestData() {
        val ts = DateTimeFormatter
                .ISO_INSTANT
                .format(Instant.now())

        val sensorType1 = SensorType(sensorTypeValue = "PHVALUE")
        val sensorType2 = SensorType(sensorTypeValue = "TEMPERATURE")

        val testMeasurement1 = Measurement(
                timestamp = ts,
                value = "3.3",
                sensorType = sensorType1
        )
        val testMeasurement2 = Measurement(
                timestamp = ts,
                value = "77.0",
                sensorType = sensorType2
        )

        val testWell1 = Well(
                name = "New Well 01",
                deviceId = "1234567",
                latitude = BigDecimal(44.7777),
                longtitude = BigDecimal(55.2223),
                altitude = 0.0,
                maxDepth = 0.0,
                diameter = 0.0,
                sensorTypes = mutableListOf(
                        sensorType1,
                        sensorType2
                ),
                measurements = mutableListOf(
                        testMeasurement1,
                        testMeasurement2
                )
        )
        wellRepository.save(testWell1)
    }
}
