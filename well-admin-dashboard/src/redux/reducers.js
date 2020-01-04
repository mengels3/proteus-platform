import { SHOW_MODAL, HIDE_MODAL, SAVE_CHANGES, DELETE_MEASUREMENT } from "./actions";
import { combineReducers } from "redux";

const initialData = [
  {
    name: "Chile-01",
    id: "e34810f0-8471-495b-aa3e-a338dc8b8de2",
    measurements: [{ name: "Temperature" }, { name: "pH" }, { name: "Water Level", parameters: [{ name: "diameter", value: 0.5 }] }]
  },
  {
    name: "Chile-02",
    id: "7c56dcca-1ec2-4b9c-9455-77830fc313e2",
    measurements: [{ name: "Temperature" }, { name: "pH" }]
  }
];

const measurementPoints = (state = initialData, action) => {
  switch (action.type) {
    case SAVE_CHANGES:
      return state.map(point => point.id === action.payload.id ? action.payload : point)
    default:
      return state
  }
};

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
      var updatedMeasurements = state.data.measurements.filter(mp => mp.name !== action.payload.name)
      console.log(action.payload)
      return Object.assign({}, state, {
        ...state,
        data: {
          ...state.data,
          measurements: updatedMeasurements
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
