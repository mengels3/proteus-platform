import { SHOW_MODAL, HIDE_MODAL } from "./actions";
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

const measurementPoints = (state = { data: initialData }, action) => {
  return state;
};

const modalVisibility = (state = { show: false, data: null }, action) => {
  switch (action.type) {
    case SHOW_MODAL:
      return Object.assign({}, state, {
        show: true,
        data: action.payload
      });
    default:
      return state;
  }
};

export default combineReducers({
  measurementPoints,
  modal: modalVisibility
});
