import React from "react";
import "./App.css";
import MeasurementPointListContainer from './container-components/MeasurementPointListContainer'
import EditMeasurementModalContainer from "./container-components/EditMeasurementModalContainer";

class App extends React.Component {

  render() {
    return (
      <>
        <MeasurementPointListContainer />
        <EditMeasurementModalContainer />
      </>
    );
  }
}

export default App;
