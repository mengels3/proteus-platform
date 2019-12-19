import React from 'react';
import './App.css';
import MeasurementListItem from './components/MeasurementListItem'

const data = [{name: "Chile-01", value: 12}, {name: "Chile-02", value: 13}]

function App() {
  return (
      <ul class="list-group">
        {data.map(item => (
          <MeasurementListItem data= {item}/>
        ))}
      </ul>
  );
}

export default App;
