import React from 'react'
import MeasurementListItem from './MeasurementListItem'

class MeasurementPointList extends React.Component {


    componentDidMount() {
        console.log("did mount")
        this.props.fetchMeasurementPoints();
    }

    render() {
        if (this.props.fetching) return (<h1>FETCHING</h1>)
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
