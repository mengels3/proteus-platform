import React from 'react';
import './MeasurementListItem.css'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faTrash, faPen, faThermometerHalf, faTint, faWater} from '@fortawesome/free-solid-svg-icons'

class MeasurementListItem extends React.Component {

    render() {
      return <div class="measurement-item-background">
                    <div class="measurement-item-overview">
                        <div class="measurement-item-overview-left">
                            <div class="column">
                                <b class="text">{this.props.data.name}</b>
                                <p class="detail-text"> {this.props.data.id}</p>
                            </div>
                            <div class="column margin-left">
                                <b class="measurements-text">Measurements</b>
                                <div class="measurements-listing-row">
                                    <div class="measurements-icon"><FontAwesomeIcon icon={faThermometerHalf}/></div>
                                    <p class="text">Temperature</p>
                                    <div class="measurements-icon"><FontAwesomeIcon icon={faTint}/></div>
                                    <p class="text">pH</p>
                                    <div class="measurements-icon"><FontAwesomeIcon icon={faWater}/></div>
                                    <p class="text">Water level</p>
                                </div>
                            </div>
                        </div>
                        <div class="icon-div">
                            <div class="list-item-icon" onClick={() => this.setState({visible:true})}><FontAwesomeIcon icon={faPen}/></div>
                            <div class="list-item-icon"><FontAwesomeIcon icon={faTrash}/></div>
                        </div>
                </div>
            </div>
            ;
    }
}

export default MeasurementListItem