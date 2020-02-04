package org.sedi.emp.restextractor

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.service.MeasurementService
import org.sedi.emp.restextractor.service.SensorTypeService
import org.sedi.emp.restextractor.service.WellService
import org.slf4j.LoggerFactory
import java.time.Instant

open class DatabaseInitializer(
        private val wellService: WellService,
        private val measurementService: MeasurementService,
        private val sensorTypeService: SensorTypeService) {

    companion object {
        private val logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)
    }

    fun initializeTestData() {
        if (wellService.findAll().count() > 0) {
            return;
        }
        val ph = sensorTypeService.findOrCreate(SensorType(sensorTypeValue = "ph"))
        val temp = sensorTypeService.findOrCreate(SensorType(sensorTypeValue = "temp"))
        val level = sensorTypeService.findOrCreate(SensorType(sensorTypeValue = "level"))

        val deviceId = "10009"
        val testWell1 = Well(
                name = "New Well 01",
                deviceId = deviceId,
                latitude = 44.7777,
                longitude = 55.2223,
                altitude = 0.0,
                maxDepth = 10.0,
                diameter = 0.0,
                sensorTypes = mutableListOf(ph, temp, level)
        )
        val savedWell = wellService.create(testWell1)

        val now = Instant.now()
        val savedMeasurementCount = (0..100)
                .asSequence()
                .map {
                    Measurement(
                            timestamp = now.minusSeconds(it * 60 * 10L),
                            value = "3.$it",
                            sensorType = ph,
                            wellId = savedWell.id
                    )
                }
                .map { measurementService.addMeasurement(it, deviceId) }
                .filter { it.isPresent }
                .count()

        logger.debug("I saved $savedMeasurementCount measurements!")
    }
}
