package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.springframework.data.repository.CrudRepository

interface MeasurementRepository : CrudRepository<Measurement, Long>
