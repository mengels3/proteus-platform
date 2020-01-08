package org.sedi.emp.restextractor.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SediDataController {

    private val log = LoggerFactory.getLogger(SediDataController::class.java)

    @PostMapping("/publish")
    fun sediData(@RequestBody data: String): ResponseEntity<String> {
        log.info(data);
        return ResponseEntity.ok("successfully processed")
    }
}
