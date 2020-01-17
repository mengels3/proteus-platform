package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.service.MeasurementService
import org.sedi.emp.restextractor.service.WellService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest
@ActiveProfiles("dev")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class MeasurementsPersistenceTest {

    @Autowired
    private lateinit var wellService: WellService

    @Autowired
    private lateinit var measurementService: MeasurementService

    @Test
    fun testMeasurementPersistence() {
        assertThat(wellService, Is(not(nullValue())))
        val well = wellService.findAll().first()
        assertThat(well, Is(not(nullValue())))
        assertThat(well.id, Is(not(nullValue())))

        val measurements1 = wellService.findMeasurementsForWell(well.id!!)
        assertThat(measurements1, Is(not(nullValue())))
        assertThat(measurements1.isPresent, Is(true))

        val oldMeasurementCount = measurements1.get().size
        assertThat(oldMeasurementCount, Is(101))

        val rawSensorData = "device_id=10009;level=0.77;temp=18.50;ph=7.345"
        val sensorData = measurementService.computeSensorData(rawSensorData)
        val result = measurementService.addMeasurement(sensorData)
        assertThat(result, Is(not(nullValue())))
        assertThat(result.isPresent, Is(true))

        val measurements2 = wellService.findMeasurementsForWell(well.id!!)
        assertThat(measurements2, Is(not(nullValue())))
        assertThat(measurements2.isPresent, Is(true))
        assertThat(measurements2.get().size, Is(oldMeasurementCount + 3))
    }
}
