package org.sedi.emp.restextractor.service

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.MeasurementRepository
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class WellService(
        private val wellRepository: WellRepository,
        private val measurementRepository: MeasurementRepository,
        private val sensorTypeService: SensorTypeService) {

    companion object {
        private val logger = LoggerFactory.getLogger(WellService::class.java)
    }

    @Transactional
    fun create(well: Well): Well {
        val savedSensorTypes = findOrCreateSensorTypes(well)
        val w2 = well.copy(sensorTypes = savedSensorTypes)
        return wellRepository.save(w2)
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

    @Transactional
    fun update(well: Well): Optional<Well> {
        logger.debug("Updating well: $well")
        val sensorTypes = findOrCreateSensorTypes(well).toList()
        val optionalWell = wellRepository.findByDeviceId(well.deviceId)
        return if (optionalWell.isPresent) {
            Optional.of(copyAndUpdate(well, optionalWell.get(), sensorTypes))
        } else {
            Optional.empty()
        }
    }

    @Transactional
    fun delete(id: UUID) {
        logger.debug("Deleting well for ID: $id")
        wellRepository.deleteById(id)
    }

    private fun copyAndUpdate(requestWell: Well, persistentWell: Well, sensorTypes: List<SensorType>): Well {
        val w = persistentWell.copy(
                altitude = requestWell.altitude,
                diameter = requestWell.diameter,
                maxDepth = requestWell.maxDepth,
                sensorTypes = sensorTypes.toMutableList()
        )
        return wellRepository.save(w)
    }

    private fun findOrCreateSensorTypes(well: Well): MutableList<SensorType> {
        return findOrCreateSensorTypes(well.sensorTypes)
    }

    private fun findOrCreateSensorTypes(sensorTypes: List<SensorType>): MutableList<SensorType> {
        return sensorTypes
                .asSequence()
                .map { sensorTypeService.findOrCreate(it) }
                .toMutableList()
    }
}
