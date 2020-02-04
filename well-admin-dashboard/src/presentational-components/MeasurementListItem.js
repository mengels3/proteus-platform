import React from "react";
import "./MeasurementListItem.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPen, faThermometerHalf, faTint, faWater } from "@fortawesome/free-solid-svg-icons";
import { getDisplayNameForSensorTypeValue } from '../utils/utils'

class MeasurementListItem extends React.Component {
  iconForMeasurement(name) {
    switch (name) {
      case "Temperature":
        return faThermometerHalf;
      case "pH":
        return faTint;
      case "Water Level":
        return faWater;
      default:
        throw Error("your devs suck.");
    }
  }

  renderMeasurement(measurement) {
    const name = getDisplayNameForSensorTypeValue(measurement.sensorTypeValue)
    const icon = this.iconForMeasurement(name)

    return (
      <div className="measurements-info">
        <div className="measurements-icon measurements-color margin-left">
          <FontAwesomeIcon icon={icon} />
        </div>
        <p className="text">{name}</p>
      </div>
    )
  }

  render() {
    console.log(this.props)
    return (
      <>
        <div className="measurement-item-background">
          <div className="measurement-item-overview">
            <div className="measurement-item-overview-left">
              <div className="column">
                <b className="text">{this.props.data.name}</b>
                <p className="detail-text"> {this.props.data.id}</p>
              </div>
              <div className="column margin-left">
                <b className="measurements-text measurements-color">Measurements</b>
                <div className="measurements-listing-row-wrap">
                  {this.props.data.sensorTypes.map(item => (
                    this.renderMeasurement(item)
                  ))}
                </div>
              </div>
            </div>
            <div className="icon-div">
              <div className="list-item-icon" onClick={() => this.props.onEdit(this.props.data)}>
                <FontAwesomeIcon icon={faPen} />
              </div>
              <div class="list-item-icon">
                <FontAwesomeIcon icon={faTrash} />
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default MeasurementListItem;
