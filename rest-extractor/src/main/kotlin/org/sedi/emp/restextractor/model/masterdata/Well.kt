package org.sedi.emp.restextractor.model.masterdata

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.sedi.emp.restextractor.model.sensordata.Measurement
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "WELL")
class Well(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "w_id")
        var id: UUID? = null,

        @Column(name = "w_device_id")
        val deviceId: String,

        @Column(name = "w_name")
        val name: String,

        @Column(name = "w_lat")
        val latitude: BigDecimal,

        @Column(name = "w_long")
        val longtitude: BigDecimal,

        @Column(name = "w_altitude")
        val altitude: Double,

        @Column(name = "w_maxdepth")
        val maxDepth: Double,

        @Column(name = "w_diameter")
        val diameter: Double,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val sensorTypes: MutableList<SensorType>,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @Fetch(value = FetchMode.SUBSELECT)
        val measurements: MutableList<Measurement>
)
