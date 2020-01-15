import React from "react";
import { Modal, Button } from "react-bootstrap";
import "./EditMeasurementModal.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { getDisplayNameForSensorTypeValue } from '../utils/utils'

class EditMeasurementModal extends React.Component {

  onSave() {
    this.props.onSave(this.props.data)
    this.props.onClose()
  }

  renderMeasurement(sensorType) {
    return (
      <div class="measurement-row">
        <p class="black-text">{getDisplayNameForSensorTypeValue(sensorType.sensorTypeValue)}</p>
        <div class="icon" onClick={() => this.props.onDeleteMeasurement(sensorType)}>
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
          <div class="measurement-listing">{this.props.data?.sensorTypes.map(sensorType => this.renderMeasurement(sensorType))}</div>
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
