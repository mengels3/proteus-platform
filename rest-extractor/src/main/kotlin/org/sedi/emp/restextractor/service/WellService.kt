package org.sedi.emp.restextractor.service

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

@Service
class WellService(
        private val wellRepository: WellRepository,
        private val measurementRepository: MeasurementRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(WellService::class.java)
    }

    @Transactional
    fun createWell(
            deviceId: String,
            name: String,
            latitude: BigDecimal,
            longtitude: BigDecimal,
            altitude: Double,
            maxDepth: Double,
            diameter: Double,
            sensorTypes: List<SensorType>): Well {
        // create the POJO:
        val well = Well(
                name = name,
                deviceId = deviceId,
                latitude = latitude,
                longtitude = longtitude,
                altitude = altitude,
                maxDepth = maxDepth,
                diameter = diameter,
                sensorTypes = sensorTypes.toMutableList(),
                measurements = mutableListOf()
        )

        // save it:
        return wellRepository.save(well)
    }

    fun findMeasurementsForWell(id: UUID): Optional<List<Measurement>> {
        logger.debug("Looking up well with ID $id ...")
        val maybeWell = findById(id)
        return if (maybeWell.isPresent) {
            val wellId = maybeWell.get().id!!
            val measurements = measurementRepository.findByWellId(wellId)
            logger.debug("Found ${measurements.count()} measurements")
            Optional.of(measurements)

        } else {
            logger.info("Couldn't find well for ID $id!")
            Optional.empty();
        }
    }

    fun findAll(): List<Well> {
        return wellRepository
                .findAll()
                .toList()
    }

    fun findById(id: UUID): Optional<Well> {
        return wellRepository.findById(id)
    }
}
