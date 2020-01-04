import React from "react";
import { Modal, Button } from "react-bootstrap";
import "./EditMeasurementModal.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

class EditMeasurementModal extends React.Component {

  onSave() {
    this.props.onSave(this.props.data)
    this.props.onClose()
  }

  renderMeasurement(measurement) {
    return (
      <div class="measurement-row">
        <p class="text">{measurement.name}</p>
        <div class="icon" onClick={() => this.props.onDeleteMeasurement(measurement)}>
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
          <Button variant="secondary" onClick={() => this.props.onClose()}>Close</Button>
          <Button variant="primary" onClick={() => this.onSave()}>Save Changes</Button>
        </Modal.Footer>
      </Modal>
    );
  }
}

export default EditMeasurementModal;
