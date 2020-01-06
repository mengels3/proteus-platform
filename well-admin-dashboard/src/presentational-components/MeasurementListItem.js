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
        <div className="measurement-item-background">
          <div className="measurement-item-overview">
            <div className="measurement-item-overview-left">
              <div className="column">
                <b className="text">{this.props.data.name}</b>
                <p className="detail-text"> {this.props.data.id}</p>
              </div>
              {/* <div className="column margin-left">
                <b className="measurements-text">Measurements</b>
                <div className="measurements-listing-row-wrap">
                  {this.props.data.measurements.map(item => (
                    <div className="measurements-info">
                      <div className="measurements-icon margin-left">
                        <FontAwesomeIcon icon={this.iconForMeasurement(item.name)} />
                      </div>
                      <p className="text">{item.name}</p>
                    </div>
                  ))}
                </div>
              </div> */}
            </div>
            <div className="icon-div">
              <div className="list-item-icon" onClick={() => this.props.onEdit(this.props.data)}>
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
