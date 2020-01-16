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
    fun create(
            deviceId: String,
            name: String,
            latitude: Double,
            longtitude: Double,
            altitude: Double,
            maxDepth: Double,
            diameter: Double,
            sensorTypes: List<SensorType>): Well {
        // some of the given sensor types might already exist:
        val savedSensorTypes = sensorTypes
                .asSequence()
                .map { sensorTypeService.findOrCreate(it) }
                .toMutableList()

        // create the POJO:
        val well = Well(
                name = name,
                deviceId = deviceId,
                latitude = latitude,
                longtitude = longtitude,
                altitude = altitude,
                maxDepth = maxDepth,
                diameter = diameter,
                sensorTypes = savedSensorTypes
        )

        // save it:
        return wellRepository.save(well)
    }

    @Transactional
    fun create(well: Well): Well {
        val savedSensorTypes = well.sensorTypes
                .asSequence()
                .map { sensorTypeService.findOrCreate(it) }
                .toMutableList()
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
    fun update(well: Well): Well {
        val optionalWell = wellRepository.findByDeviceId(well.deviceId)
        return if (optionalWell.isPresent) {
            val w = optionalWell.get()

            // TODO: make these immutable values again and use deepCopy()!
            w.altitude = well.altitude
            w.diameter = well.diameter
            w.maxDepth = well.maxDepth

            wellRepository.save(w)

        } else {
            wellRepository.save(well)
        }
    }
}
