import React from 'react'
import MeasurementListItem from './MeasurementListItem'

class MeasurementPointList extends React.Component{

    render(){
        return(
            <ul class="list-group">
                {this.props.measurementPoints.map(mPoint => (
                    <MeasurementListItem data={mPoint}/>
                ))}
            </ul>
        )
    }
}

export default MeasurementPointList
