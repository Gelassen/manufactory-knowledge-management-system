import React, { useState } from 'react';
import axios from 'axios';
import config from './config';  // Import configuration file

function MachineDetails() {
  const [barcode, setBarcode] = useState('');  // State to hold the barcode value
  const [machine, setMachine] = useState(null);  // State to hold the machine object
  const [loading, setLoading] = useState(false);  // Loading indicator state
  const [error, setError] = useState(null);  // Error state

  // Function to handle the API request based on barcode
  const fetchMachineData = () => {
    if (!barcode) {
      setError('Please enter a barcode');
      return;
    }

    setLoading(true);  // Set loading to true
    setError(null);    // Clear errors

    // Sending the API request with the barcode parameter
    axios.get(`${config.API_URL}/machine?barcode=${barcode}`)
      .then((response) => {
        const machineData = response.data.data; // Extract the machine data from the response
        setMachine(machineData);  // Set the machine data
        setLoading(false);  // Set loading to false
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setError('Failed to fetch machine data');
        setLoading(false);  // Set loading to false in case of error
      });
  };

  return (
    <div>
      <h1>Machine Details</h1>
      
      {/* Input field for barcode */}
      <input
        type="text"
        placeholder="Enter machine barcode"
        value={barcode}
        onChange={(e) => setBarcode(e.target.value)}  // Update barcode state on input change
      />
      <button onClick={fetchMachineData}>Fetch Machine</button>

      {/* Display loading state */}
      {loading && <div>Loading...</div>}
      
      {/* Display error message if any */}
      {error && <div>{error}</div>}

      {/* Display machine data if available */}
      {machine && (
        <div>
          <p><strong>Name:</strong> {machine.name}</p>
          <p><strong>Manufacturer:</strong> {machine.manufacturer}</p>
          <p><strong>Barcode:</strong> {machine.barcode}</p>
          
          <h3>Breakdowns:</h3>
          <ul>
            {machine.breakdowns && machine.breakdowns.length > 0 ? (
              machine.breakdowns.map((breakdown) => (
                <li key={breakdown.id}>
                  <strong>Failure:</strong> {breakdown.failure}
                  <p><strong>Solution:</strong> {breakdown.solution}</p>
                  <p><strong>Date:</strong> {new Date(breakdown.dateTime).toLocaleString()}</p>
                </li>
              ))
            ) : (
              <p>No breakdowns available.</p>
            )}
          </ul>
        </div>
      )}
    </div>
  );
}

export default MachineDetails;
