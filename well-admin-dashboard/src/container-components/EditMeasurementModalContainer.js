import { connect } from "react-redux";
import EditMeasurementModal from "../presentational-components/EditMeasurementModal";
import { closeModal, saveChanges, deleteMeasurement } from '../redux/actions'

const mapStateToProps = state => ({
  data: state.modal.data,
  show: state.modal.show
});

const mapDispatchToProps = dispatch => ({
  onClose: () => dispatch(closeModal()),
  onSave: (measurementPoint) => dispatch(saveChanges(measurementPoint)),
  onDeleteMeasurement: (measurement) => dispatch(deleteMeasurement(measurement))
})

const EditMeasurementModalContainer = connect(mapStateToProps, mapDispatchToProps)(EditMeasurementModal);

export default EditMeasurementModalContainer;
