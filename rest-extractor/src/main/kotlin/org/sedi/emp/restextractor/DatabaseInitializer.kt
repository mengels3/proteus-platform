package org.sedi.emp.restextractor

import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import java.time.Instant
import java.time.format.DateTimeFormatter

class DatabaseInitializer(private val measurementRepository: MeasurementRepository) {

    fun initializeTestData() {
        val ts = DateTimeFormatter
                .ISO_INSTANT
                .format(Instant.now())

        val testMeasurement1 = Measurement(
                timestamp = ts,
                phValue = 3.3,
                temperature = 33.0,
                waterLevel = 2.5,
                wellId = 1L
        )
        measurementRepository.save(testMeasurement1)
    }
}
