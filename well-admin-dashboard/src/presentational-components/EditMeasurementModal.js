import React from "react";
import { Modal, Button } from "react-bootstrap";
import "./EditMeasurementModal.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

class EditMeasurementModal extends React.Component {
  deleteMeasurement(measurement) {
    // little hack because i suck at react apparently
    const sData = { ...this.getData() };
    const updatedMeasurements = sData.measurements.filter(m => m.name !== measurement.name);

    sData.measurements = updatedMeasurements;

    this.setState({
      data: sData
    });
  }

  onClose() {
    this.setState({ data: null });
    this.props.onClose();
  }

  onSave() {
    this.props.onSave(this.getData());
    this.setState({ data: null });
  }

  renderMeasurement(measurement) {
    return (
      <div class="measurement-row">
        <p class="text">{measurement.name}</p>
        <div class="icon" onClick={() => this.deleteMeasurement(measurement)}>
          <FontAwesomeIcon icon={faTimes} />
        </div>
      </div>
    );
  }

  render() {
    return (
      <Modal show={this.props.show} onHide={() => this.props.onClose()}>
        <Modal.Header closeButton>
          <Modal.Title>{this.props.data?.name}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div class="measurement-listing">{this.props.data?.measurements.map(measurement => this.renderMeasurement(measurement))}</div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary">Close</Button>
          <Button variant="primary">Save Changes</Button>
        </Modal.Footer>
      </Modal>
    );
  }
}

export default EditMeasurementModal;
