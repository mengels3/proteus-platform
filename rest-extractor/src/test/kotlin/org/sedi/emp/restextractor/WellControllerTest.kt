package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class WellControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testMeasurementRetrievalForWell() {
        assertThat(restTemplate, Is(not(nullValue())))
        val wellResponse: ResponseEntity<Array<Well>> = restTemplate
                .getForEntity("/well", Array<Well>::class.java)

        assertThat(wellResponse, Is(not(nullValue())))
        assertThat(wellResponse.statusCode, Is(HttpStatus.OK))
        assertThat(wellResponse.body, Is(not(nullValue())))

        val wellId = wellResponse
                .body!!
                .asSequence()
                .filter { it.name == "New Well 01" }
                .first()
                .id

        assertThat(restTemplate, Is(not(nullValue())))
        val measurementsResponse: ResponseEntity<Array<Measurement>> = restTemplate
                .getForEntity("/well/{wellId}/measurements", Array<Measurement>::class.java, wellId)

        assertThat(measurementsResponse, Is(not(nullValue())))
        assertThat(measurementsResponse.statusCode, Is(HttpStatus.OK))
        assertThat(measurementsResponse.body, Is(not(nullValue())))
        assertThat(measurementsResponse.body!!.size, Is(2))
    }

    @Test
    fun testWellCreationQuery() {
        val level = SensorType(sensorTypeValue = "level")
        val well = Well(
                name = "New Well 02",
                deviceId = "10002",
                latitude = 54.777,
                longitude = 65.223,
                altitude = 1.0,
                maxDepth = 2.0,
                diameter = 3.0,
                sensorTypes = mutableListOf(level)
        )
        val response: ResponseEntity<Well> = restTemplate
                .postForEntity("/well", well, Well::class.java)
        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body?.id, Is(not(nullValue())))
    }

    @Test
    fun testWellUpdateQuery() {
        val level = SensorType(sensorTypeValue = "level")
        val temp = SensorType(sensorTypeValue = "temp")
        val well = Well(
                name = "New Well 33",
                deviceId = "10009",
                latitude = 54.777,
                longitude = 65.223,
                altitude = 1.0,
                maxDepth = 2.0,
                diameter = 3.0,
                sensorTypes = mutableListOf(temp, level)
        )
        val request: HttpEntity<Well> = HttpEntity(well)

        val response: ResponseEntity<Well> = restTemplate
                .exchange("/well", HttpMethod.PUT, request, Well::class.java)
        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body?.id, Is(not(nullValue())))
    }
}
