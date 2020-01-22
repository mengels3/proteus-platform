import React from "react";
import "./App.css";
import MeasurementPointListContainer from './container-components/MeasurementPointListContainer'
import EditMeasurementModal from './presentational-components/EditMeasurementModal'
import AddMeasurementPointButton from './presentational-components/AddMeasurementPointButton'
import AddMeasurementPointModal from './presentational-components/AddMeasurementPointModal'
import { Navbar } from 'react-bootstrap'
import drop from './assets/drop.png'
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';

const ctheme = createMuiTheme({
  palette: {
    primary: { main: '#42a5f5' },
    secondary: { main: '#0069c0' },
  }
});


class App extends React.Component {

  render() {
    return (
      <MuiThemeProvider theme={ctheme}>
        <Navbar className="primary-background-color" variant="dark">
          <Navbar.Brand href="#home">
            <img
              alt=""
              src={drop}
              width="30"
              height="30"
              className="d-inline-block align-top white"
            />{' '}
            Proteus Well Management System
          </Navbar.Brand>
        </Navbar>
        <MeasurementPointListContainer />
        <AddMeasurementPointButton />
        <EditMeasurementModal />
        <AddMeasurementPointModal />
      </MuiThemeProvider>
    );
  }
}

export default App;
