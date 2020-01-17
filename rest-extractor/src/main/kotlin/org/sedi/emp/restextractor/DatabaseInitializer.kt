package org.sedi.emp.restextractor

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import java.math.BigDecimal
import java.time.Instant

open class DatabaseInitializer(
        private val wellRepository: WellRepository,
        private val measurementRepository: MeasurementRepository) {

    fun initializeTestData() {
        val ph = SensorType(sensorTypeValue = "ph")
        val temp = SensorType(sensorTypeValue = "temp")
        val level = SensorType(sensorTypeValue = "level")

        val testWell1 = Well(
                name = "New Well 01",
                deviceId = "10009",
                latitude = BigDecimal(44.7777),
                longtitude = BigDecimal(55.2223),
                altitude = 0.0,
                maxDepth = 0.0,
                diameter = 0.0,
                sensorTypes = mutableListOf(ph, temp, level)
        )
        val savedWell = wellRepository.save(testWell1)

        val now = Instant.now()
        for (n in 0 until 100) {
            val testMeasurement1 = Measurement(
                    timestamp = now.minusSeconds(n * 60 * 10L),
                    value = "3."+n,
                    sensorType = ph,
                    wellId = savedWell.id
            )
            val testMeasurement2 = Measurement(
                    timestamp = now.minusSeconds(n * 60 * 10L),
                    value = "7"+n+".0",
                    sensorType = level,
                    wellId = savedWell.id
            )
            measurementRepository.save(testMeasurement1)
            measurementRepository.save(testMeasurement2)
        }
    }
}
