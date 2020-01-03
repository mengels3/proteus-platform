import React from "react";
import "./App.css";
import MeasurementPointListContainer from './container-components/MeasurementPointListContainer'
import EditMeasurementModalContainer from "./container-components/EditMeasurementModalContainer";

class App extends React.Component {

  showEditModal(data) {
    this.setState({
      selectedData: data,
      showEditModal: true
    });
  }

  onEditModalSave(updatedMeasurementItem) {
    const replaceIndex = this.state.data.findIndex(item => item.id === updatedMeasurementItem.id);
    console.log(replaceIndex);

    var dataCopy = this.state.data.slice();
    dataCopy[replaceIndex] = updatedMeasurementItem;

    console.log("save");
    console.log(dataCopy);

    this.setState({
      showEditModal: false,
      data: dataCopy
    });
  }

  hideEditModal() {
    this.setState({ showEditModal: false });
  }

  deleteMeasurementPoint(id) {
    console.log("delete: " + id);
    const deleteIndex = this.state.data.findIndex(item => item.id === id);
    const dataCopy = this.state.data.slice();

    dataCopy.splice(deleteIndex, 1);

    this.setState({
      data: dataCopy
    });
  }

  render() {
    return (
      <>
        <MeasurementPointListContainer/>
        <EditMeasurementModalContainer/>
      </>
    );
  }
}

export default App;
