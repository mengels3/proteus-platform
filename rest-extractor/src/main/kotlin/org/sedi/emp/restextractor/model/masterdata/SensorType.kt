package org.sedi.emp.restextractor.model.masterdata

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "SENSOR_TYPE")
data class SensorType(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "st_id")
        var id: UUID? = null,

        @Column(name = "st_value", unique = true)
        val sensorTypeValue: String // TODO: turn into an enum {"PHVALUE", ...}
)
