package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.persistence.WellRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest
@ActiveProfiles("dev")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class MasterDataPersistenceTest {

    @Autowired
    private lateinit var wellRepository: WellRepository

    @Test
    @Disabled
    fun testWellMasterDataPersistence() {
        assertThat(wellRepository, Is(not(nullValue())))
        assertThat(wellRepository.count(), Is(0L))

        val testWell1 = Well(
                name = "New Well 01",
                deviceId = "456456456",
                latitude = BigDecimal(33.2222),
                longtitude = BigDecimal(22.5689),
                altitude = 0.0,
                maxDepth = 0.0,
                diameter = 0.0,
                sensorTypes = mutableListOf(),
                measurements = mutableListOf()
        )

        wellRepository.save(testWell1)
        assertThat(wellRepository.count(), Is(1L))
    }
}
