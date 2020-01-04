import React from 'react'
import MeasurementListItem from './MeasurementListItem'

class MeasurementPointList extends React.Component {

    render() {
        return (
            <ul className="list-group">
                {this.props.measurementPoints.map(mPoint => (
                    <MeasurementListItem data={mPoint} onEdit={() => this.props.onItemEdit(mPoint)} />
                ))}
            </ul>
        )
    }
}

export default MeasurementPointList
