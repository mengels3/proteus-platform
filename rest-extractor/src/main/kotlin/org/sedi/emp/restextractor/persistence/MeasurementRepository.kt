package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MeasurementRepository : CrudRepository<Measurement, UUID>
