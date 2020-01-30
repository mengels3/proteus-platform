package org.sedi.emp.restextractor

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.service.MeasurementService
import org.sedi.emp.restextractor.service.SensorTypeService
import org.sedi.emp.restextractor.service.WellService
import java.time.Instant

open class DatabaseInitializer(
        private val wellService: WellService,
        private val measurementService: MeasurementService,
        private val sensorTypeService: SensorTypeService) {

    fun initializeTestData() {
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
                maxDepth = 0.0,
                diameter = 0.0,
                sensorTypes = mutableListOf(ph, temp, level)
        )
        val savedWell = wellService.create(testWell1)

        val now = Instant.now()
        val testMeasurement1 = Measurement(
                timestamp = now,
                value = "3.3",
                sensorType = ph,
                wellId = savedWell.id
        )
        val testMeasurement2 = Measurement(
                timestamp = now,
                value = "77.0",
                sensorType = temp,
                wellId = savedWell.id
        )
        measurementService.addMeasurement(testMeasurement1, deviceId)
        measurementService.addMeasurement(testMeasurement2, deviceId)
    }
}
