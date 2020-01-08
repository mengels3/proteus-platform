import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useSelector, useDispatch } from 'react-redux'
import { Form } from 'react-bootstrap'


const AddMeasurementPointModal = () => {

    const dispatch = useDispatch()
    const show = useSelector(state => state.createModal.show)

    return (
        <Modal show={show} onHide={() => dispatch({ type: 'HIDE_CREATE_MODAL' })}>
            <Modal.Header closeButton>
                <Modal.Title>Create a new well</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formBasicName">
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" placeholder="Enter name" />
                    </Form.Group>

                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" placeholder="Password" />
                    </Form.Group>
                    <Form.Group controlId="formBasicCheckbox">
                        <Form.Check type="checkbox" label="Check me out" />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    )
}

export default AddMeasurementPointModal;