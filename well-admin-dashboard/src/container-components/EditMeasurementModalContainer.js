import { connect } from 'react-redux'
import EditMeasurementModal from '../presentational-components/EditMeasurementModal'

const mapStateToProps = state => ({
    data: state.data,
    show: state.show
})

const EditMeasurementModalContainer = connect(mapStateToProps)(EditMeasurementModal)

export default EditMeasurementModalContainer