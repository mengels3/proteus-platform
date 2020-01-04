package org.sedi.emp.restextractor.model.sensordata

import javax.persistence.*

@Entity
@Table(name = "MEASUREMENT")
class Measurement(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "m_id")
        var id: Long = 0,

        @Column(name = "m_timestamp")
        val timestamp: String,

        @Column(name = "m_wellid")
        // TODO: @ManyToOne(...)
        val wellId: Long,

        @Column(name = "m_water_level")
        val waterLevel: Double,

        @Column(name = "m_phvalue")
        val phValue: Double,

        @Column(name = "m_temperature")
        val temperature: Double
)
