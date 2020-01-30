import {
  connect
} from 'react-redux'
import MeasurementPointList from '../presentational-components/MeasurementPointList'
import {
  showModal,
  fetchMeasurementPointsStart,
  fetchMeasurementPointsError,
  fetchMeasurementPointsSuccess
} from '../redux/actions'
import axios from 'axios'
import config from '../config'

const mapStateToProps = state => ({
  measurementPoints: state.measurementPoints.data,
  fetching: state.measurementPoints.fetching,
  hasError: state.measurementPoints.error
})

const mapDispatchToProps = dispatch => ({
  onItemEdit: item => dispatch(showModal(item)),
  fetchMeasurementPoints: () => {
    dispatch(fetchMeasurementPointsStart())
    return axios.get(`${config.backend.url}/well`)
      .then(res => dispatch(fetchMeasurementPointsSuccess(res.data)))
      .catch(err => dispatch(fetchMeasurementPointsError(err)))
  }
})

const MeasurementPointListCointainer = connect(mapStateToProps, mapDispatchToProps)(MeasurementPointList)

export default MeasurementPointListCointainer