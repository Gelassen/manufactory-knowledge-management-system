import React, { useState } from 'react';
import { Button, TextField, Typography, Grid, CircularProgress, Paper, Box, List, ListItem, ListItemText } from '@mui/material';
import axios from 'axios';
import config from './config';

function MachineDetails() {
  const [barcode, setBarcode] = useState('');
  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch machine data by barcode
  const fetchMachineData = () => {
    if (!barcode) {
      setError('Please enter a barcode');
      return;
    }

    setLoading(true);
    setError(null);

    axios
      .get(`${config.API_URL}/machine?barcode=${barcode}`)
      .then((response) => {
        setMachine(response.data.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setError('Failed to fetch machine data');
        setLoading(false);
      });
  };

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '100vh',
        bgcolor: 'background.default',
        padding: '20px',
      }}
    >
      <Paper sx={{ padding: 4, width: '100%', maxWidth: 600 }}>
        <Typography variant="h4" gutterBottom>
          Machine Details
        </Typography>

        <TextField
          label="Enter machine barcode"
          variant="outlined"
          fullWidth
          value={barcode}
          onChange={(e) => setBarcode(e.target.value)}
          sx={{ marginBottom: 2 }}
        />

        <Button
          variant="contained"
          color="primary"
          fullWidth
          onClick={fetchMachineData}
          sx={{ marginBottom: 2 }}
        >
          Fetch Machine
        </Button>

        {/* Display loading spinner */}
        {loading && (
          <Box display="flex" justifyContent="center" sx={{ marginBottom: 2 }}>
            <CircularProgress />
          </Box>
        )}

        {/* Display error message */}
        {error && (
          <Typography variant="body1" color="error" sx={{ marginBottom: 2 }}>
            {error}
          </Typography>
        )}

        {/* Display machine data */}
        {machine && (
          <Box>
            <Typography variant="h6" gutterBottom>
              <strong>Name:</strong> {machine.name}
            </Typography>
            <Typography variant="body1">
              <strong>Manufacturer:</strong> {machine.manufacturer}
            </Typography>
            <Typography variant="body1">
              <strong>Barcode:</strong> {machine.barcode}
            </Typography>

            <Typography variant="h6" sx={{ marginTop: 2 }}>
              Breakdowns:
            </Typography>
            <List>
              {machine.breakdowns && machine.breakdowns.length > 0 ? (
                machine.breakdowns.map((breakdown) => (
                  <ListItem key={breakdown.id}>
                    <ListItemText
                      primary={<strong>{breakdown.failure}</strong>}
                      secondary={
                        <>
                          <Typography variant="body2">{breakdown.solution}</Typography>
                          <Typography variant="body2">
                            <strong>Date:</strong> {new Date(breakdown.dateTime).toLocaleString()}
                          </Typography>
                        </>
                      }
                    />
                  </ListItem>
                ))
              ) : (
                <Typography variant="body1">No breakdowns available.</Typography>
              )}
            </List>
          </Box>
        )}
      </Paper>
    </Box>
  );
}

export default MachineDetails;
