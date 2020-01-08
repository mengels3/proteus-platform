import React from "react";
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import "./AddMeasurementPointButton.css";
import { useDispatch } from 'react-redux'


const AddMeasurementPointButton = () => {

    const dispatch = useDispatch()
    return (
        <div className="fixedbutton">
            <Fab onClick={() => dispatch({ type: "SHOW_CREATE_MODAL" })} color="primary" aria-label="add">
                <AddIcon />
            </Fab>
        </div>
    )
}

export default AddMeasurementPointButton;