package org.sedi.emp.restextractor.model.sensordata

import org.sedi.emp.restextractor.model.masterdata.SensorType
import java.time.Instant

data class SensorData(
        val deviceId: String,
        val timestamp: Instant,
        val dataPoints: List<Pair<SensorType, String>>
)
