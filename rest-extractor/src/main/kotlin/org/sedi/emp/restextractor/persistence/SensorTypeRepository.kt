package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SensorTypeRepository: CrudRepository<SensorType, UUID> {

    fun findBySensorTypeValue(sensorTypeValue: String): Optional<SensorType>
}
