import React from "react";
import "./App.css";
import MeasurementPointListContainer from './container-components/MeasurementPointListContainer'
import EditMeasurementModal from './presentational-components/EditMeasurementModal'
import AddMeasurementPointButton from './presentational-components/AddMeasurementPointButton'
import AddMeasurementPointModal from './presentational-components/AddMeasurementPointModal'
import { Navbar } from 'react-bootstrap'
import drop from './assets/drop.png'


class App extends React.Component {

  render() {
    return (
      <>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="#home">
            <img
              alt=""
              src={drop}
              width="30"
              height="30"
              className="d-inline-block align-top"
            />{' '}
            Nehalennia Well Management
    </Navbar.Brand>
        </Navbar>
        <MeasurementPointListContainer />
        <AddMeasurementPointButton />
        <EditMeasurementModal />
        <AddMeasurementPointModal />
      </>
    );
  }
}

export default App;
