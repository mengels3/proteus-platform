import React, { useEffect } from "react";
import { Modal, InputGroup } from "react-bootstrap"
import { useSelector, useDispatch } from 'react-redux'
import { Form, Col } from 'react-bootstrap'
import { Formik } from 'formik'
import * as yup from 'yup'
import Select from 'react-select'
import axios from 'axios'
import { addMeasuementPoint, fetchSensorTypesStart, fetchSensorTypesError, fetchSensorTypesSuccess, hideCreateModal } from '../redux/actions'
import { getDisplayNameForSensorTypeValue } from '../utils/utils'


const schema = yup.object({
    name: yup.string().min(4, 'Please select name a name longer than 4 characters').max(30, 'Please select name a name no longer than 30 characters').required('Please provide a name.'),
    maxDepth: yup.number().required('Please provide the well depth.'),
    diameter: yup.number().required('Please provide the well diameter.'),
    sensorTypes: yup.array().min(1, 'At least one measurement is required.'),
    deviceId: yup.string().min(3, 'Please provide a device id longer that 2 characters.').max(20, 'Please provide a device no longer than 20 characters.').required(),
    altitude: yup.number().required(),
    longitude: yup.number().required(),
    latitude: yup.number().required()
});

const AddMeasurementPointModal = () => {

    const dispatch = useDispatch()
    const show = useSelector(state => state.createModal.show)

    const onSubmit = data => {
        const newData = { ...data, sensorTypes: data.sensorTypes.map(st => ({ sensorTypeValue: st.value })) }
        axios.post('http://localhost:8080/well', newData)
            .then(res => {
                console.log(`Created new well and server answered with status code ${res.status}.`)
                dispatch(addMeasuementPoint(res.data))
                dispatch(hideCreateModal())
            })
    }

    useEffect(() => {
        console.log("fetching sensor types")
        dispatch(fetchSensorTypesStart())
        axios.get('http://localhost:8080/sensor-type')
            .then(res => dispatch(fetchSensorTypesSuccess(res.data)))
            .catch(err => dispatch(fetchSensorTypesError(err)))
    }, [])

    const sensorTypes = useSelector(state => state.sensorTypes.data).map(stype => ({ value: stype.sensorTypeValue, label: getDisplayNameForSensorTypeValue(stype.sensorTypeValue) }))

    if (sensorTypes.fetching) return ("LOADING")

    return (
        <Modal show={show} onHide={() => dispatch({ type: 'HIDE_CREATE_MODAL' })}>
            <Modal.Header closeButton>
                <Modal.Title>Creating new well</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Formik validationSchema={schema} onSubmit={(data) => onSubmit(data)} initialValues={{ name: '', depth: undefined, diameter: undefined, sensorTypes: [], deviceId: '', altitude: undefined, longitude: undefined, latitude: undefined }}>
                    {({
                        handleSubmit,
                        handleChange,
                        values,
                        touched,
                        errors,
                        handleBlur,
                        setFieldValue
                    }) => (
                            <Form noValidate onSubmit={handleSubmit}>
                                <Form.Group controlId="formBasicName">
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control type="text" name="name" value={values.name} onBlur={handleBlur} onChange={handleChange} isInvalid={touched.name && !!errors.name} placeholder="Enter name" />
                                    <Form.Control.Feedback type="invalid">{errors.name}</Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group controlId="formBasicDeviceId">
                                    <Form.Label>Device ID</Form.Label>
                                    <Form.Control type="text" name="deviceId" value={values.deviceId} onBlur={handleBlur} onChange={handleChange} isInvalid={touched.deviceId && !!errors.deviceId} placeholder="Enter device id" />
                                    <Form.Control.Feedback type="invalid">{errors.deviceId}</Form.Control.Feedback>
                                </Form.Group>
                                <Form.Row>
                                    <Form.Group as={Col} controlId="formBasicDepth">
                                        <Form.Label>Depth</Form.Label>
                                        <InputGroup>
                                            <Form.Control type="number" name="maxDepth" value={values.maxDepth} onBlur={handleBlur} onChange={handleChange} isInvalid={touched.maxDepth && !!errors.maxDepth} placeholder="Enter depth" />
                                            <InputGroup.Append>
                                                <InputGroup.Text>meters</InputGroup.Text>
                                            </InputGroup.Append>
                                            <Form.Control.Feedback type="invalid">{errors.maxDepth}</Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                    <Form.Group as={Col} controlId="formBasicDiameter">
                                        <Form.Label>Diameter</Form.Label>
                                        <InputGroup>
                                            <Form.Control type="number" name="diameter" onBlur={handleBlur} value={values.diameter} onChange={handleChange} isInvalid={touched.diameter && !!errors.diameter} placeholder="Enter diameter" />
                                            <InputGroup.Append>
                                                <InputGroup.Text>meters</InputGroup.Text>
                                            </InputGroup.Append>
                                            <Form.Control.Feedback type="invalid">{errors.diameter}</Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                </Form.Row>
                                <Form.Row>
                                    <Form.Group as={Col} controlId="formBasicLatitude">
                                        <Form.Label>Latitude</Form.Label>
                                        <InputGroup>
                                            <Form.Control type="number" name="latitude" value={values.latitude} onBlur={handleBlur} onChange={handleChange} isInvalid={touched.latitude && !!errors.latitude} placeholder="Latitude" />
                                            <InputGroup.Append>
                                                <InputGroup.Text>°</InputGroup.Text>
                                            </InputGroup.Append>
                                            <Form.Control.Feedback type="invalid">{errors.latitude}</Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                    <Form.Group as={Col} controlId="formBasicLongitude">
                                        <Form.Label>Longitude</Form.Label>
                                        <InputGroup>
                                            <Form.Control type="number" name="longitude" onBlur={handleBlur} value={values.longitude} onChange={handleChange} isInvalid={touched.longitude && !!errors.longitude} placeholder="Longitude" />
                                            <InputGroup.Append>
                                                <InputGroup.Text>°</InputGroup.Text>
                                            </InputGroup.Append>
                                            <Form.Control.Feedback type="invalid">{errors.longitude}</Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                    <Form.Group as={Col} controlId="formBasicAltitude">
                                        <Form.Label>Altitude</Form.Label>
                                        <InputGroup>
                                            <Form.Control type="number" name="altitude" value={values.altitude} onBlur={handleBlur} onChange={handleChange} isInvalid={touched.altitude && !!errors.altitude} placeholder="Altitude" />
                                            <InputGroup.Append>
                                                <InputGroup.Text>m</InputGroup.Text>
                                            </InputGroup.Append>
                                            <Form.Control.Feedback type="invalid">{errors.altitude}</Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                </Form.Row>
                                <Form.Group>
                                    <Form.Label>Measurements</Form.Label>
                                    <Select
                                        defaultValue={[]}
                                        isMulti
                                        name="sensorTypes"
                                        options={sensorTypes}
                                        onChange={(sensorTypes) => setFieldValue('sensorTypes', sensorTypes)}
                                        onBlur={handleBlur}
                                        value={values.sensorTypes}
                                    />
                                </Form.Group>
                                <button type="submit">Submit</button>
                                <p>{JSON.stringify(values)}</p>
                            </Form>
                        )}
                </Formik>
            </Modal.Body>
        </Modal>
    )
}

export default AddMeasurementPointModal;