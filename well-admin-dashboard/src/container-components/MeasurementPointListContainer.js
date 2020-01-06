import {
  connect
} from 'react-redux'
import MeasurementPointList from '../presentational-components/MeasurementPointList'
import {
  showModal,
  fetchUsersStart,
  fetchUsersError,
  fetchUsersSuccess
} from '../redux/actions'
import axios from 'axios'

const mapStateToProps = state => ({
  measurementPoints: state.measurementPoints.data,
  fetching: state.measurementPoints.fetching,
  hasError: state.measurementPoints.error
})

const mapDispatchToProps = dispatch => ({
  onItemEdit: item => dispatch(showModal(item)),
  fetchMeasurementPoints: () => {
    dispatch(fetchUsersStart())
    return axios.get('http://localhost:8081/wells')
      .then(res => dispatch(fetchUsersSuccess(res.data)))
      .catch(err => dispatch(fetchUsersError(err)))
  }
})

const MeasurementPointListCointainer = connect(mapStateToProps, mapDispatchToProps)(MeasurementPointList)

export default MeasurementPointListCointainer