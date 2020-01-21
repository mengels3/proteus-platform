import axios from 'axios'

export const SHOW_MODAL = 'SHOW_MODAL'
export const HIDE_MODAL = 'HIDE_MODAL'
export const SAVE_CHANGES = 'SAVE_CHANGES'
export const DELETE_MEASUREMENT = 'DELETE_MEASUREMENT'
export const ADD_MEASUREMENT_POINT = 'ADD_MEASUREMENT_POINT'

export const FETCH_MEASUREMENT_POINTS_START = 'FETCH_MEASUREMENT_POINTS_START'
export const FETCH_MEASUREMENT_POINTS_SUCCESS = 'FETCH_MEASUREMENT_POINTS_SUCCESS'
export const FETCH_MEASUREMENT_POINTS_ERROR = 'FETCH_MEASUREMENT_POINTS_ERROR'

export const SHOW_CREATE_MODAL = 'SHOW_CREATE_MODAL'
export const HIDE_CREATE_MODAL = 'HIDE_CREATE_MODAL'
export const CREATE_MODAL_IS_VALID = 'CREATE_MODAL_IS_VALID'

export const FETCH_SENSOR_TYPES_START = 'FETCH_SENSOR_TYPES_START'
export const FETCH_SENSOR_TYPES_SUCCESS = 'FETCH_SENSOR_TYPES_SUCCESS'
export const FETCH_SENSOR_TYPES_ERROR = 'FETCH_SENSOR_TYPES_ERROR'

export const markCreateModalAsValid = () => ({
    type: CREATE_MODAL_IS_VALID
})

export const showCreateModal = () => ({
    type: SHOW_CREATE_MODAL,
})

export const hideCreateModal = () => ({
    type: HIDE_CREATE_MODAL
})

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

export const fetchSensorTypesStart = () => ({
    type: FETCH_SENSOR_TYPES_START
})

export const fetchSensorTypesSuccess = (data) => ({
    type: FETCH_SENSOR_TYPES_SUCCESS,
    payload: data
})

export const fetchSensorTypesError = (error) => ({
    type: FETCH_SENSOR_TYPES_ERROR,
    payload: error
})

export const fetchUsersStart = () => ({
    type: FETCH_MEASUREMENT_POINTS_START
})

export const fetchUsersSuccess = (data) => ({
    type: FETCH_MEASUREMENT_POINTS_SUCCESS,
    payload: data
})

export const fetchUsersError = (err) => ({
    type: FETCH_MEASUREMENT_POINTS_ERROR,
    payload: err
})

export const addMeasuementPoint = (measurementPoint) => ({
    type: ADD_MEASUREMENT_POINT,
    payload: measurementPoint
})