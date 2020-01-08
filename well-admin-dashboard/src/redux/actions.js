import axios from 'axios'

export const SHOW_MODAL = 'SHOW_MODAL'
export const HIDE_MODAL = 'HIDE_MODAL'
export const SAVE_CHANGES = 'SAVE_CHANGES'
export const DELETE_MEASUREMENT = 'DELETE_MEASUREMENT'

export const FETCH_MEASUREMENT_POINTS_START = 'FETCH_MEASUREMENT_POINTS_START'
export const FETCH_MEASUREMENT_POINTS_SUCCESS = 'FETCH_MEASUREMENT_POINTS_SUCCESS'
export const FETCH_MEASUREMENT_POINTS_ERROR = 'FETCH_MEASUREMENT_POINTS_ERROR'

export const SHOW_CREATE_MODAL = 'SHOW_CREATE_MODAL'
export const HIDE_CREATE_MODAL = 'HIDE_CREATE_MODAL'

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