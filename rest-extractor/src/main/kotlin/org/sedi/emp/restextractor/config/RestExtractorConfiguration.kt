package org.sedi.emp.restextractor.config

import org.sedi.emp.restextractor.DatabaseInitializer
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RestExtractorConfiguration {

    private val logger = LoggerFactory.getLogger(RestExtractorConfiguration::class.java)

    @Bean
    fun initialize(databaseInitializer: DatabaseInitializer) = CommandLineRunner {
        logger.debug("Running DatabaseInitializer() ...")
        databaseInitializer.initializeTestData()
        logger.debug("done!")
    }

    @Bean
    fun databaseInitializer(
            wellRepository: WellRepository,
            measurementRepository: MeasurementRepository): DatabaseInitializer {
        return DatabaseInitializer(
                wellRepository = wellRepository,
                measurementRepository = measurementRepository
        )
    }
}
