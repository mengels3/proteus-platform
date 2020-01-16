package org.sedi.emp.restextractor.model.masterdata

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "WELL")
data class Well(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "w_id")
        var id: UUID? = null,

        @Column(name = "w_device_id")
        val deviceId: String,

        @Column(name = "w_name")
        var name: String,

        @Column(name = "w_lat")
        var latitude: Double,

        @Column(name = "w_long")
        var longtitude: Double,

        @Column(name = "w_altitude")
        var altitude: Double,

        @Column(name = "w_maxdepth")
        var maxDepth: Double,

        @Column(name = "w_diameter")
        var diameter: Double,

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var sensorTypes: MutableList<SensorType>
)
