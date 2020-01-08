export const getDisplayNameForSensorTypeValue = (sensorTypeValue) => {
    switch (sensorTypeValue) {
        case "PHVALUE": return "pH"
        case "TEMPERATURE": return "Temperature"
        default: return "unknown sensor"
    }
}