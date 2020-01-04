import {
  connect
} from 'react-redux'
import MeasurementPointList from '../presentational-components/MeasurementPointList'
import {
  showModal
} from '../redux/actions'

const mapStateToProps = state => ({
  measurementPoints: state.measurementPoints
})

const mapDispatchToProps = dispatch => ({
  onItemEdit: item => dispatch(showModal(item))
})

const MeasurementPointListCointainer = connect(mapStateToProps, mapDispatchToProps)(MeasurementPointList)

export default MeasurementPointListCointainer