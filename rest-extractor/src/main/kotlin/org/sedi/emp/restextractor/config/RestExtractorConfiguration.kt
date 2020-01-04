package org.sedi.emp.restextractor.config

import org.sedi.emp.restextractor.DatabaseInitializer
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RestExtractorConfiguration {

    private val logger = LoggerFactory.getLogger(RestExtractorConfiguration::class.java)

    @Bean
    fun initialize(databaseInitializer: DatabaseInitializer) = CommandLineRunner {
        logger.info("Running DatabaseInitializer() ...")
        databaseInitializer.initializeTestData()
    }

    @Bean
    fun databaseInitializer(measurementRepository: MeasurementRepository): DatabaseInitializer {
        return DatabaseInitializer(measurementRepository)
    }
}
