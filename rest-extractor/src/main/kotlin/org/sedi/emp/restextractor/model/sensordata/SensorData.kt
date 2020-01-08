package org.sedi.emp.restextractor.model.sensordata

/**
 * The sensor data send via the LoRa gateway.
 */
data class SensorData (
        val deviceId: String,
        val timestamp: String,
        val dataPoints: List<Pair<String, String>>
)
