import { connect } from 'react-redux'
import MeasurementPointList from '../presentational-components/MeasurementPointList'

const mapStateToProps = state => {
    return {
      measurementPoints: state.measurementPoints
    }
  }

const MeasurementPointListCointainer = connect(mapStateToProps)(MeasurementPointList)

export default MeasurementPointListCointainer