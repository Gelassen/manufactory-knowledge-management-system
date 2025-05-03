import React, { useState, useEffect } from 'react';
import { 
    Button, 
    Card, 
    Menu, 
    MenuItem, 
    Typography, 
    Box, 
    Paper,
    IconButton,
    TextField, 
    CircularProgress, 
    List, 
    ListItem, 
    ListItemText 
  } from "@mui/material";
import { format } from 'date-fns';
import config from '../config';
import { useParams } from 'react-router-dom';
import axios from 'axios';


function MachineDetails() {
  const { id } = useParams();
  
  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const cancelToken = axios.CancelToken.source();

  useEffect(() => {
    const cancelToken = axios.CancelToken.source();
    
    axios
      .get(`${config.API_URL}/machine/${id}`, {
        cancelToken: cancelToken.token
      })
      .then((response) => {
        setMachine(response.data.data);
        setLoading(false);
      })
      .catch((error) => {
        if (axios.isCancel(error)) {
          console.log("Request canceled:", error.message);
        } else {
          setError('Failed to fetch machine data');
          setLoading(false);
        }
      });
  
    return () => {
      cancelToken.cancel("Component unmounted, request canceled");
    };
  }, [id]); 

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


        {loading && !machine && (
            <Box display="flex" justifyContent="center" sx={{ marginBottom: 2 }}>
                <CircularProgress />
            </Box>
        )}

        {error && !machine && (
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
                            <strong>Date:</strong> {format(new Date(breakdown.dateTime), 'yyyy-MM-dd HH:mm')}
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