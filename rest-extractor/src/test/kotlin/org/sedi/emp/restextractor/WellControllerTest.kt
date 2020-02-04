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

        val measurementsResponse: ResponseEntity<Array<Measurement>> = restTemplate
                .getForEntity("/well/{wellId}/measurements", Array<Measurement>::class.java, wellId)

        assertThat(measurementsResponse, Is(not(nullValue())))
        assertThat(measurementsResponse.statusCode, Is(HttpStatus.OK))
        assertThat(measurementsResponse.body, Is(not(nullValue())))
        assertThat(measurementsResponse.body!!.size, Is(101))
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
        val wellToUpdate = "New Well 01"

        // get the ID of the well:
        assertThat(restTemplate, Is(not(nullValue())))
        val wellResponse: ResponseEntity<Array<Well>> = restTemplate
                .getForEntity("/well", Array<Well>::class.java)
        assertThat(wellResponse, Is(not(nullValue())))
        assertThat(wellResponse.statusCode, Is(HttpStatus.OK))
        assertThat(wellResponse.body, Is(not(nullValue())))

        val wellBeforeUpdate: Well = wellResponse
                .body!!
                .asSequence()
                .filter { it.name == wellToUpdate }
                .first()

        // update the well:
        val level = SensorType(sensorTypeValue = "level")
        val temp = SensorType(sensorTypeValue = "temp")
        val newMaxDepth = wellBeforeUpdate.maxDepth + 200
        val well = Well(
                name = wellToUpdate,
                deviceId = "10009",
                latitude = 54.777,
                longitude = 65.223,
                altitude = 1.0,
                maxDepth = newMaxDepth,
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
        assertThat(response.body?.id!!, Is(wellBeforeUpdate.id!!))
        assertThat(response.body?.maxDepth!!, Is(newMaxDepth))

        // fetch the well again and check its latitude:
        val wellAfterUpdateResponse: ResponseEntity<Well> = restTemplate
                .getForEntity("/well/{id}", Well::class.java, wellBeforeUpdate.id)
        assertThat(wellAfterUpdateResponse, Is(not(nullValue())))
        assertThat(wellAfterUpdateResponse.statusCode, Is(HttpStatus.OK))
        assertThat(wellAfterUpdateResponse.body, Is(not(nullValue())))
        val wellAfterUpdate: Well = wellAfterUpdateResponse.body!!
        assertThat(wellAfterUpdate.id!!, Is(wellBeforeUpdate.id!!))
        assertThat(wellAfterUpdate.maxDepth, Is(newMaxDepth))
    }


    @Test
    fun testWellDeletion() {
        val wellResponse1: ResponseEntity<Array<Well>> = restTemplate
                .getForEntity("/well", Array<Well>::class.java)

        assertThat(wellResponse1, Is(not(nullValue())))
        assertThat(wellResponse1.statusCode, Is(HttpStatus.OK))
        assertThat(wellResponse1.body, Is(not(nullValue())))
        val wellCount1 = wellResponse1.body?.size!!

        val id: String = wellResponse1
                .body!!
                .asSequence()
                .filter { it.name == "New Well 01" }
                .first()
                .id!!
                .toString()

        restTemplate.delete("/well/{id}", id)

        val wellResponse2: ResponseEntity<Array<Well>> = restTemplate
                .getForEntity("/well", Array<Well>::class.java)
        assertThat(wellResponse2, Is(not(nullValue())))
        assertThat(wellResponse2.statusCode, Is(HttpStatus.OK))
        assertThat(wellResponse2.body, Is(not(nullValue())))
        assertThat(wellResponse2.body?.size!!, Is(wellCount1 - 1))
    }
}
