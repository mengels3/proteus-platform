package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.persistence.WellRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class WellController(private val wellRepository: WellRepository) {

    private val logger = LoggerFactory.getLogger(WellController::class.java)

    @GetMapping("/well")
    fun getAllWells(): List<Well> {
        val wells = wellRepository
                .findAll()
                .toList()

        logger.debug("Returning ${wells.size} well(s)")
        return wells
    }

    @GetMapping("/well/{id}/measurements")
    fun getMeasurementsForWell(@PathVariable id: UUID): ResponseEntity<List<Measurement>> {
        logger.debug("Looking up well with ID $id")
        val maybeWell = wellRepository.findById(id)
        return if (maybeWell.isPresent) {
            val measurements = maybeWell
                    .get()
                    .measurements
            logger.debug("Returning ${measurements.count()} measurements")
            ResponseEntity.ok(measurements)
        } else {
            logger.info("Couldn't find well for ID $id!")
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
