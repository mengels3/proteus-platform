package org.sedi.emp.restextractor.model.masterdata

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.sedi.emp.restextractor.model.sensordata.Measurement
import javax.persistence.*

@Entity
@Table(name = "WELL")
class Well(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "w_id")
        var id: Long = 0,

        @Column(name = "w_name")
        val name: String,

        @Column(name = "w_lat")
        val latitude: String,

        @Column(name = "w_long")
        val longtitude: String,

        @Column(name = "w_altitude")
        val altitude: Double,

        @Column(name = "w_maxdepth")
        val maxDepth: Double,

        @Column(name = "w_diameter")
        val diameter: Double,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val sensorTypes: MutableList<SensorType>,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @Fetch(value = FetchMode.SUBSELECT)
        val measurements: MutableList<Measurement>
)
