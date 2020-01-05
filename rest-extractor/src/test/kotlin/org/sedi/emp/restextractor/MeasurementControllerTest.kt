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
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // TODO: the PostgreSQL create statement (see "dev" profile) does not work with H2
class MeasurementControllerTest {

    @Autowired
    private lateinit var measurementsController: MeasurementsController

    @Autowired
    private lateinit var measurementRepository: MeasurementRepository

    @Autowired
    private lateinit var databaseInitializer: DatabaseInitializer

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
        measurementRepository.deleteAll()
        databaseInitializer.initializeTestData()
    }

    @Test
    fun testMeasurementRetrieval() {
        assertThat(measurementsController, Is(not(nullValue())))
        assertThat(restTemplate, Is(not(nullValue())))

        val response: ResponseEntity<Array<Measurement>> = restTemplate
                .getForEntity("/measurements", Array<Measurement>::class.java)
        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body!!.size, Is(1))
    }
}
