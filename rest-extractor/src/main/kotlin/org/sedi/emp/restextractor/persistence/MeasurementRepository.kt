package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.sensordata.Measurement
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.*

interface MeasurementRepository : CrudRepository<Measurement, UUID> {

    fun findByWellId(wellId: UUID): List<Measurement>
    @Query("SELECT * FROM MEASUREMENT JOIN SENSOR_TYPE ON sensor_type_st_id = st_id WHERE m_fk_wellid = ?1 AND st_value = ?2 AND m_timestamp between ?3 AND ?4 ORDER BY m_timestamp DESC",nativeQuery = true)
    fun loadHistoricData(wellId: UUID?, sensorType:String, fromDate:LocalDateTime, toDate:LocalDateTime): List<Measurement>
}
