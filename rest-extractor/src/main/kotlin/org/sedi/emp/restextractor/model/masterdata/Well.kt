package org.sedi.emp.restextractor.model.masterdata

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "WELL")
data class Well(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "w_id")
        var id: UUID? = null,

        @Column(name = "w_device_id", unique = true)
        val deviceId: String,

        @Column(name = "w_name", unique = true)
        val name: String,

        @Column(name = "w_lat")
        val latitude: Double,

        @Column(name = "w_long")
        val longitude: Double,

        @Column(name = "w_altitude")
        val altitude: Double,

        @Column(name = "w_maxdepth")
        val maxDepth: Double,

        @Column(name = "w_diameter")
        val diameter: Double,

        @ManyToMany(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
        val sensorTypes: MutableList<SensorType>
)
