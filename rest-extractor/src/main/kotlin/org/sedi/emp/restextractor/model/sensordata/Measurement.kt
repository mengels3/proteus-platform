package org.sedi.emp.restextractor.model.sensordata

import org.sedi.emp.restextractor.model.masterdata.SensorType
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "MEASUREMENT")
data class Measurement(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "m_id")
        var id: UUID? = null,

        @Column(name = "m_timestamp")
        val timestamp: Instant,

        @Column(name = "m_value")
        val value: String, // an arbitrary value

        @ManyToOne
        val sensorType: SensorType,

        @Column(name = "m_fk_wellid")
        val wellId: UUID? = null
)
