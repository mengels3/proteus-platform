import { ADD_MEASUREMENT_POINT, FETCH_SENSOR_TYPES_START, FETCH_SENSOR_TYPES_SUCCESS, FETCH_SENSOR_TYPES_ERROR, CREATE_MODAL_IS_VALID, SHOW_MODAL, HIDE_MODAL, SAVE_CHANGES, DELETE_MEASUREMENT, FETCH_MEASUREMENT_POINTS_ERROR, FETCH_MEASUREMENT_POINTS_SUCCESS, fetchMeasurementPointsStart, SHOW_CREATE_MODAL, HIDE_CREATE_MODAL } from "./actions";
import { combineReducers } from "redux"
import axios from 'axios'

const measurementPoints = (state = { data: [], fetching: false, error: null }, action) => {
  switch (action.type) {
    case fetchMeasurementPointsStart:
      return { ...state, fetching: true }
    case FETCH_MEASUREMENT_POINTS_SUCCESS:
      return { ...state, fetching: false, data: action.payload }
    case FETCH_MEASUREMENT_POINTS_ERROR:
      return { ...state, fetching: false, error: action.payload }
    case ADD_MEASUREMENT_POINT:
      return { ...state, data: [...state.data, action.payload] }
    default: return state
  }
}

const sensorTypes = (state = { data: [], fetching: false, error: null }, action) => {
  switch (action.type) {
    case FETCH_SENSOR_TYPES_START:
      return { ...state, fetching: true }
    case FETCH_SENSOR_TYPES_SUCCESS:
      return { ...state, fetching: false, data: action.payload }
    case FETCH_SENSOR_TYPES_ERROR:
      return { ...state, fetching: false, error: action.payload }
    default: return state
  }
}

const modalVisibility = (state = { show: false, data: null }, action) => {
  switch (action.type) {
    case SHOW_MODAL:
      return Object.assign({}, state, {
        show: true,
        data: JSON.parse(JSON.stringify(action.payload))
      });
    case HIDE_MODAL:
      return Object.assign({}, state, {
        show: false,
        data: null
      });
    case DELETE_MEASUREMENT:
      var updatedSensorTypes = state.data.sensorTypes.filter(mp => mp.sensorTypeValue !== action.payload.sensorTypeValue)
      return Object.assign({}, state, {
        ...state,
        data: {
          ...state.data,
          sensorTypes: updatedSensorTypes
        }
      });
    default:
      return state;
  }
};

const createModal = (state = { show: false, data: null, validated: false }, action) => {
  switch (action.type) {
    case SHOW_CREATE_MODAL:
      return { ...state, show: true }
    case HIDE_CREATE_MODAL:
      return { ...state, show: false }
    case CREATE_MODAL_IS_VALID:
      return { ...state, validated: true }
    default: return state
  }
}

export default combineReducers({
  measurementPoints: measurementPoints,
  modal: modalVisibility,
  createModal: createModal,
  sensorTypes: sensorTypes
});
