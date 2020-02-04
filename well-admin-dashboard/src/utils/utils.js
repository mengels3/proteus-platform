export const getDisplayNameForSensorTypeValue = (sensorTypeValue) => {
    switch (sensorTypeValue) {
        case "ph": return "pH"
        case "temp": return "Temperature"
        case "level": return "Water Level"
        default: return "unknown sensor"
    }
}