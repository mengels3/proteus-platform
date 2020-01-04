package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.controller.MeasurementsController
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.format.DateTimeFormatter
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest
@ActiveProfiles("test") // TODO: the PostgreSQL create statement (see "dev" profile) does not work with H2
class MeasurementsPersistenceTest {

    @Autowired
    private lateinit var measurementsController: MeasurementsController

    @Autowired
    private lateinit var measurementRepository: MeasurementRepository

    @BeforeEach
    fun setUp() {
        measurementRepository.deleteAll()
    }

    @Test
    fun testMeasurementPersistence() {
        assertThat(measurementRepository, Is(not(nullValue())))
        assertThat(measurementRepository.count(), Is(0L))

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
        val persistedMeasurement1 = measurementRepository.save(testMeasurement1)
        assertThat(measurementRepository.count(), Is(1L))
        assertThat(persistedMeasurement1, Is(testMeasurement1))
        assertThat(persistedMeasurement1.id, Is(not(0L)))
    }

    @Test
    fun testMeasurementRetreival() {
        assertThat(measurementsController, Is(not(nullValue())))
        val measurementsCount = measurementsController
                .getMeasurements()
                .count()
        assertThat(measurementsCount, Is(0))
    }
}
