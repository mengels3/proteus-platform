package org.sedi.emp.restextractor.model.masterdata

import javax.persistence.*

@Entity
@Table(name = "SENSOR_TYPE")
class SensorType (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "st_id")
    var id: Long = 0,

    @Column(name = "st_value")
    val sensorTypeValue: String // TODO: turn into an enum {"PHVALUE", ...}
)
