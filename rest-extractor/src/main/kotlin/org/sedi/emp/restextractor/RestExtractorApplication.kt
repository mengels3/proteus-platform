package org.sedi.emp.restextractor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestExtractorApplication

fun main(args: Array<String>) {
	runApplication<RestExtractorApplication>(*args)
}
