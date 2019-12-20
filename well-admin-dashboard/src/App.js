import React from 'react';
import './App.css';
import MeasurementListItem from './components/MeasurementListItem'

const data = [{name: "Chile-01", id: "e34810f0-8471-495b-aa3e-a338dc8b8de2", value: 12}, {name: "Chile-02", id: "7c56dcca-1ec2-4b9c-9455-77830fc313e2", value: 13}]

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
