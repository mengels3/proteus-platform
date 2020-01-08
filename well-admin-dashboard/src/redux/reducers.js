import { SHOW_MODAL, HIDE_MODAL, SAVE_CHANGES, DELETE_MEASUREMENT, FETCH_MEASUREMENT_POINTS_ERROR, FETCH_MEASUREMENT_POINTS_SUCCESS, FETCH_MEASUREMENT_POINTS_START } from "./actions";
import { combineReducers } from "redux";

// const initialData = [
//   {
//     name: "Chile-01",
//     id: "e34810f0-8471-495b-aa3e-a338dc8b8de2",
//     measurements: [{ name: "Temperature" }, { name: "pH" }, { name: "Water Level", parameters: [{ name: "diameter", value: 0.5 }] }]
//   },
//   {
//     name: "Chile-02",
//     id: "7c56dcca-1ec2-4b9c-9455-77830fc313e2",
//     measurements: [{ name: "Temperature" }, { name: "pH" }]
//   }
// ];

const measurementPoints = (state = { data: [], fetching: false, error: null }, action) => {
  switch (action.type) {
    case FETCH_MEASUREMENT_POINTS_START:
      return { ...state, fetching: true }
    case FETCH_MEASUREMENT_POINTS_SUCCESS:
      return { ...state, fetching: false, data: action.payload }
    case FETCH_MEASUREMENT_POINTS_ERROR:
      return { ...state, fetching: false, error: action.payload }
    case SAVE_CHANGES:
      return {
        ...state,
        data: state.data.map(point => point.id === action.payload.id ? action.payload : point)
      }
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

export default combineReducers({
  measurementPoints: measurementPoints,
  modal: modalVisibility
});
