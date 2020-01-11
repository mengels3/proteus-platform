import React from "react";
import { Modal, Button, InputGroup, Dropdown } from "react-bootstrap"
import { useSelector, useDispatch } from 'react-redux'
import { Form, Col } from 'react-bootstrap'
import { Formik, FieldArray } from 'formik';
import * as yup from 'yup'
import Select from 'react-select';

const schema = yup.object({
    name: yup.string().min(4, 'Please select name a name longer than 4 characters').max(30, 'Please select name a name no longer than 30 characters').required('Please provide a name.'),
    depth: yup.number().required('Please provide the well depth.'),
    diameter: yup.number().required('Please provide the well diameter.'),
    measurements: yup.array().min(1, 'At least one measurement is required.')
});

const AddMeasurementPointModal = () => {

    const dispatch = useDispatch()
    const show = useSelector(state => state.createModal.show)

    const onSubmit = data => {
        console.log("submit")
        console.log(data)

    }

    const sensorTypes = [{ value: "PH", label: "PH" }, { value: "TEMPERATURE", label: "TEMPERATURE" }]

    return (
        <Modal show={show} onHide={() => dispatch({ type: 'HIDE_CREATE_MODAL' })}>
            <Modal.Header closeButton>
                <Modal.Title>Create a new well</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Formik validationSchema={schema} onSubmit={(data) => onSubmit(data)} initialValues={{ name: '', depth: undefined, diameter: undefined, measurements: [] }}>
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
                                <Form.Row>
                                    <Form.Group as={Col} controlId="formBasicDepth">
                                        <Form.Label>Depth</Form.Label>
                                        <InputGroup>
                                            <Form.Control type="number" name="depth" value={values.depth} onBlur={handleBlur} onChange={handleChange} isInvalid={touched.depth && !!errors.depth} placeholder="Enter depth" />
                                            <InputGroup.Append>
                                                <InputGroup.Text>meters</InputGroup.Text>
                                            </InputGroup.Append>
                                            <Form.Control.Feedback type="invalid">{errors.depth}</Form.Control.Feedback>
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
                                <Form.Group>
                                    <Form.Label>Measurements</Form.Label>
                                    <Select
                                        defaultValue={[]}
                                        isMulti
                                        name="measurements"
                                        options={sensorTypes}
                                        onChange={(measurements) => setFieldValue('measurements', measurements)}
                                        onBlur={handleBlur}
                                        value={values.measurements}
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