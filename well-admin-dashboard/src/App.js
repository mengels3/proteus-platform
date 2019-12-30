import React from "react";
import "./App.css";
import MeasurementListItem from "./components/MeasurementListItem";
import EditMeasurementModal from "./components/EditMeasurementModal";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [
        {
          name: "Chile-01",
          id: "e34810f0-8471-495b-aa3e-a338dc8b8de2",
          measurements: [
            { name: "Temperature" },
            { name: "pH" },
            { name: "Water Level", parameters: [{ name: "diameter", value: 0.5 }] }
          ]
        },
        {
          name: "Chile-02",
          id: "7c56dcca-1ec2-4b9c-9455-77830fc313e2",
          measurements: [{ name: "Temperature" }, { name: "pH" }]
        }
      ],
      showEditModal: false,
      selectedData: null
    };
  }

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

  render() {
    return (
      <>
        <ul class="list-group">
          {this.state.data.map(item => (
            <MeasurementListItem data={item} onEdit={() => this.showEditModal(item)} />
          ))}
        </ul>
        <EditMeasurementModal
          data={this.state.selectedData}
          show={this.state.showEditModal}
          onClose={() => this.hideEditModal()}
          onSave={updatedMeasurementItem => this.onEditModalSave(updatedMeasurementItem)}
        />
      </>
    );
  }
}

export default App;
