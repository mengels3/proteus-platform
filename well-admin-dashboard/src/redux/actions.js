export const SHOW_MODAL = 'SHOW_MODAL'
export const HIDE_MODAL = 'HIDE_MODAL'
export const SAVE_CHANGES = 'SAVE_CHANGES'
export const DELETE_MEASUREMENT = 'DELETE_MEASUREMENT'

export const showModal = (measurementPoint) => ({
    type: SHOW_MODAL,
    payload: measurementPoint
})

export const closeModal = () => ({
    type: HIDE_MODAL
})

export const saveChanges = (measurementPoint) => ({
    type: SAVE_CHANGES,
    payload: measurementPoint
})

export const deleteMeasurement = (measurement) => ({
    type: DELETE_MEASUREMENT,
    payload: measurement
})