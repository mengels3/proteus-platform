import React from "react";
import "./App.css";
import MeasurementPointListContainer from './container-components/MeasurementPointListContainer'
import EditMeasurementModalContainer from "./container-components/EditMeasurementModalContainer";
import AddMeasurementPointButton from './presentational-components/AddMeasurementPointButton'
import AddMeasurementPointModal from './presentational-components/AddMeasurementPointModal'

class App extends React.Component {

  render() {
    return (
      <>
        <MeasurementPointListContainer />
        <AddMeasurementPointButton />
        <EditMeasurementModalContainer />
        <AddMeasurementPointModal />
      </>
    );
  }
}

export default App;
