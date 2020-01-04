package org.sedi.emp.restextractor.model.masterdata

import javax.persistence.*

@Entity
@Table(name = "WELL")
class Well(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
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
        val maxdepth: Double,

        @Column(name = "w_diameter")
        val diameter: Double
)
