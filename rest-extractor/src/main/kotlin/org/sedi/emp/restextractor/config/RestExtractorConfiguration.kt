package org.sedi.emp.restextractor.config

import org.sedi.emp.restextractor.DatabaseInitializer
import org.sedi.emp.restextractor.service.MeasurementService
import org.sedi.emp.restextractor.service.SensorTypeService
import org.sedi.emp.restextractor.service.WellService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

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
            wellService: WellService,
            measurementService: MeasurementService,
            sensorTypeService: SensorTypeService): DatabaseInitializer {
        return DatabaseInitializer(
                wellService = wellService,
                measurementService = measurementService,
                sensorTypeService = sensorTypeService
        )
    }

    @Bean
    fun commonsRequestLoggingFilter(): CommonsRequestLoggingFilter {
        val commonsRequestLoggingFilter = CommonsRequestLoggingFilter()
        commonsRequestLoggingFilter.setIncludeClientInfo(true)
        commonsRequestLoggingFilter.setIncludeQueryString(true)
        commonsRequestLoggingFilter.setIncludeHeaders(false)
        commonsRequestLoggingFilter.setIncludePayload(true)
        commonsRequestLoggingFilter.setMaxPayloadLength(10000)
        return commonsRequestLoggingFilter
    }
}
