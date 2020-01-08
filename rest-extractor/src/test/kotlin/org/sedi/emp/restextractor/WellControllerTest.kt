package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
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
    fun testWellsRetrieval() {
        val wells = fetchWells()
        assertThat(wells, Is(not(nullValue())))
    }

    @Test
    fun testMeasurementRetrievalForWell() {
        val wellId = fetchWells()[0].id

        assertThat(restTemplate, Is(not(nullValue())))
        val response: ResponseEntity<Array<Measurement>> = restTemplate
                .getForEntity("/well/{wellId}/measurements", Array<Measurement>::class.java, wellId)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body!!.size, Is(2))
    }

    private fun fetchWells(): List<Well> {
        assertThat(restTemplate, Is(not(nullValue())))
        val response: ResponseEntity<Array<Well>> = restTemplate
                .getForEntity("/well", Array<Well>::class.java)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body!!.size, Is(1))

        return response
                .body
                ?.toList()!!
    }
}
