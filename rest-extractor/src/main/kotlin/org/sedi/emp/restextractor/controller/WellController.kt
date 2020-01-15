package org.sedi.emp.restextractor.controller

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.model.masterdata.Well
import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.sedi.emp.restextractor.service.WellService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
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

    @GetMapping("/well/{id}/measurements")
    fun getMeasurementsForWell(@PathVariable id: UUID): ResponseEntity<List<Measurement>> {
        logger.debug("getMeasurementsForWell($id)")
        return ResponseEntity.of(wellService.findMeasurementsForWell(id))
    }

    @PostMapping("/well")
    fun postNewWell(well: Well): Well {
        return wellService.createWell(well)
    }
}
