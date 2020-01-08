package org.sedi.emp.restextractor.model.sensordata

import org.sedi.emp.restextractor.model.masterdata.SensorType

data class SensorData(
        val deviceId: String,
        val timestamp: String,
        val dataPoints: List<Pair<SensorType, String>>
)
