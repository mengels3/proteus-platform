import React from "react";
import "./MeasurementListItem.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPen, faThermometerHalf, faTint, faWater } from "@fortawesome/free-solid-svg-icons";

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

  render() {
    return (
      <>
        <div class="measurement-item-background">
          <div class="measurement-item-overview">
            <div class="measurement-item-overview-left">
              <div class="column">
                <b class="text">{this.props.data.name}</b>
                <p class="detail-text"> {this.props.data.id}</p>
              </div>
              <div class="column margin-left">
                <b class="measurements-text">Measurements</b>
                <div class="measurements-listing-row-wrap">
                  {this.props.data.measurements.map(item => (
                    <div class="measurements-info">
                      <div class="measurements-icon margin-left">
                        <FontAwesomeIcon icon={this.iconForMeasurement(item.name)} />
                      </div>
                      <p class="text">{item.name}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>
            <div class="icon-div">
              <div class="list-item-icon" onClick={() => this.props.onEdit()}>
                <FontAwesomeIcon icon={faPen} />
              </div>
              {/* <div class="list-item-icon" onClick={() => this.props.onDelete(this.props.data.id)}>
                <FontAwesomeIcon icon={faTrash} />
              </div> */}
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default MeasurementListItem;
