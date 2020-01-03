import { connect } from 'react-redux'
import MeasurementListItem from '../presentational-components/MeasurementListItem'
import {showModal} from '../redux/actions'

const mapDispatchToProps = dispatch => ({
    onEdit: measurementPoint => dispatch(showModal(measurementPoint))
  })

const MeasurementPointListCointainer = connect(mapDispatchToProps)(MeasurementListItem)

export default MeasurementPointListCointainer