package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.service.WellService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class WellController(private val wellService: WellService) {

    companion object {
        private val logger = LoggerFactory.getLogger(WellController::class.java)
    }

    @GetMapping("/well")
    fun getAllWells(): List<Well> {
        val wells = wellService.findAll()
        logger.debug("Returning ${wells.size} well(s)")
        return wells
    }

    @GetMapping("/well/{id}")
    fun getWell(@PathVariable id: UUID): ResponseEntity<Well> {
        val well = wellService.findById(id)
        logger.debug("Found a well? ${well.isPresent}")
        return ResponseEntity.of(well)
    }

    @GetMapping("/well/{id}/measurements")
    fun getMeasurementsForWell(@PathVariable id: UUID): ResponseEntity<List<Measurement>> {
        logger.debug("getMeasurementsForWell($id)")
        return ResponseEntity.of(wellService.findMeasurementsForWell(id))
    }

    @PostMapping("/well")
    fun postNewWell(@RequestBody well: Well): Well {
        logger.debug("Got a new well: $well")
        val savedWell = wellService.create(well)
        logger.debug("Returning saved well: $savedWell")
        return savedWell
    }

    @PutMapping("/well")
    fun updateWell(@RequestBody well: Well): ResponseEntity<Well> {
        logger.debug("Updating well...")
        return ResponseEntity.of(wellService.update(well))
    }

    @DeleteMapping("/well/{id}")
    fun deleteWell(@PathVariable id: UUID): ResponseEntity<String> {
        logger.debug("Deleting well...")
        wellService.delete(id)
        return ResponseEntity.ok("")
    }
}
