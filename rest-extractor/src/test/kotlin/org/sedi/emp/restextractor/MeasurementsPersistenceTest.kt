package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.format.DateTimeFormatter
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest
@ActiveProfiles("dev")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class MeasurementsPersistenceTest {

    @Autowired
    private lateinit var measurementRepository: MeasurementRepository

    @Autowired
    private lateinit var wellRepository: WellRepository

    @Test
    fun testMeasurementPersistence() {
        val wells = wellRepository.findAll()
        assertThat(wells, Is(not(nullValue())))
        assertThat(wells.count(), Is(greaterThan(0)))

        val testWell: Well = wells.first()
        assertThat(testWell, Is(not(nullValue())))
        assertThat(testWell.sensorTypes.count(), Is(greaterThan(0)))

        // this must be part of the "business logic":  check if the given
        // sensor type is compatible with the well (here we'll just pick one):
        val sensorType: SensorType = testWell
                .sensorTypes
                .first()

        val ts = DateTimeFormatter
                .ISO_INSTANT
                .format(Instant.now())

        val testMeasurement1 = Measurement(
                timestamp = ts,
                value = "3.3",
                sensorType = sensorType
        )
        measurementRepository.save(testMeasurement1)
        assertThat(measurementRepository.count(), Is(3L))
    }
}
