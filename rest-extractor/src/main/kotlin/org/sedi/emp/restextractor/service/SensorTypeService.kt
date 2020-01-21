package org.sedi.emp.restextractor.service

import org.sedi.emp.restextractor.model.masterdata.SensorType
import org.sedi.emp.restextractor.persistence.SensorTypeRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SensorTypeService(private val sensorTypeRepository: SensorTypeRepository) {

    @Transactional
    fun findOrCreate(sensorType: SensorType): SensorType {
        // does a ST with the same ID exist?
        if (sensorType.id != null) {
            val optionalSensorType = sensorTypeRepository.findById(sensorType.id!!)
            if (optionalSensorType.isPresent) {
                return optionalSensorType.get()
            }
        }

        // does a ST with the same name exist?
        val optionalSensorType = sensorTypeRepository
                .findBySensorTypeValue(sensorType.sensorTypeValue)

        return if (optionalSensorType.isPresent) {
            optionalSensorType.get()
        } else {
            // no, then create one:
            sensorTypeRepository.save(sensorType)
        }
    }
}
