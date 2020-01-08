package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
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
class MeasurementControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testMeasurementRetrieval() {
        assertThat(restTemplate, Is(not(nullValue())))
        val response: ResponseEntity<Array<Measurement>> = restTemplate
                .getForEntity("/measurement", Array<Measurement>::class.java)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body!!.size, Is(2))
    }

    @Test
    fun testNewSensorDataPublishing() {
        assertThat(restTemplate, Is(not(nullValue())))
        val request = "device_id=10009;level=0.77;temp=18.50;ph=7.345"
        val response: ResponseEntity<String> = restTemplate
                .postForEntity("/publish", request, String::class.java)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body, Is("true"))
    }
}
