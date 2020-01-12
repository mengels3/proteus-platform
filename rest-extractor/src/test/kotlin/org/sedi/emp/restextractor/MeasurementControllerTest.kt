package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
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
        assertThat(response.body!!.size, Is(greaterThan(0)))
    }

    @Test
    fun testNewSensorDataPublishing() {
        val requestBody = "data=device_id=10009;level=0.77;temp=18.50;ph=7.345"
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val request: HttpEntity<String> = HttpEntity(requestBody, httpHeaders)

        assertThat(restTemplate, Is(not(nullValue())))
        val response: ResponseEntity<Array<Measurement>> = restTemplate
                .postForEntity("/publish", request, Array<Measurement>::class.java)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body!!.size, Is(3))
    }

    @Test
    fun testSensorDataPublishingWithUrlEncoding() {
        val urlEncodedBody = "data=device_id%3D10009%3Blevel%3D0.95%3Btemp%3D20.20%3Bph%3D7.10%3Blong%3D1000.00%3Blat%3D1000.00"
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val request: HttpEntity<String> = HttpEntity(urlEncodedBody, httpHeaders)

        assertThat(restTemplate, Is(not(nullValue())))
        val response: ResponseEntity<Array<Measurement>> = restTemplate
                .postForEntity("/publish", request, Array<Measurement>::class.java)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.OK))
        assertThat(response.body, Is(not(nullValue())))
        assertThat(response.body!!.size, Is(5))
    }

    @Test
    fun testPublishForUnknownWell() {
        val urlEncodedBody = "data=device_id%3D10010%3Blevel%3D0.95%3Btemp%3D20.20%3Bph%3D7.10%3Blong%3D1000.00%3Blat%3D1000.00"
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val request: HttpEntity<String> = HttpEntity(urlEncodedBody, httpHeaders)

        assertThat(restTemplate, Is(not(nullValue())))
        val response: ResponseEntity<String> = restTemplate
                .postForEntity("/publish", request, String::class.java)

        assertThat(response, Is(not(nullValue())))
        assertThat(response.statusCode, Is(HttpStatus.NOT_FOUND))
    }
}
