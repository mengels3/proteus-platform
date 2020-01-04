package org.sedi.emp.restextractor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.persistence.WellRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest
@ActiveProfiles("test") // TODO: the PostgreSQL create statement (see "dev" profile) does not work with H2
class MasterDataPersistenceTest {

    @Autowired
    private lateinit var wellRepository: WellRepository

    @Test
    fun testWellMasterDataPersistence() {
        assertThat(wellRepository, Is(not(nullValue())))
        assertThat(wellRepository.count(), Is(0L))

        val testWell1 = Well(
                name = "New Well 01",
                latitude = "51° 28′ 38″ N",
                longtitude = "0°",
                altitude = 0.0,
                maxdepth = 0.0,
                diameter = 0.0
        )

        val persistedWell = wellRepository.save(testWell1)
        assertThat(wellRepository.count(), Is(1L))
        assertThat(persistedWell, Is(testWell1))
        assertThat(persistedWell.id, Is(not(0L)))
    }
}
