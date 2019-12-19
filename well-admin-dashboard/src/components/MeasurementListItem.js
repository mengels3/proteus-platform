import React from 'react';
import './MeasurementListItem.css'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faTrash, faPen} from '@fortawesome/free-solid-svg-icons'

class MeasurementListItem extends React.Component {
    render() {
      return <div class="rounded-borders">
                <b class="text">{this.props.data.name}</b>
                <div class="icon-div">
                    <div class="list-item-icon"><FontAwesomeIcon icon={faPen}/></div>
                    <div class="list-item-icon"><FontAwesomeIcon icon={faTrash}/></div>
                </div>
            </div>;
    }
}

export default MeasurementListItem