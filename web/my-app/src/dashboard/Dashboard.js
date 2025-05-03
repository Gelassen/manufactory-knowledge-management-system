import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button, Card, Menu, MenuItem, Typography, Box, Paper,
  TextField, CircularProgress, List, ListItem, ListItemText 
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import axios from 'axios';
import config from '../config';     

const Dashboard = () => {
  // const [anchorEl, setAnchorEl] = useState(null);
  // const open = Boolean(anchorEl);

  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  // const classes = useStyles();

  // const handleMenuClick = (event) => {
  //   setAnchorEl(event.currentTarget);
  // };

  // const handleMenuClose = () => {
  //   setAnchorEl(null);
  // };

    // Fetch machine data by barcode
  const fetchMachines = () => {

    setLoading(true);
    setError(null);

    axios
      .get(`${config.API_URL}/machine`)
      .then((response) => {
        setMachine(response.data?.data ?? null);
        setLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setError('Failed to fetch machine data');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchMachines();
  }, []);

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

        {/* Display loading spinner */}
        {loading && (
          <Box display="flex" justifyContent="center" sx={{ marginBottom: 2 }}>
            <CircularProgress />
          </Box>
        )}

        {/* Display error message */}
        {error && (
          <>
            <Typography variant="body1" color="error" sx={{ marginBottom: 2 }}>
              {error}
            </Typography>
            <Button onClick={fetchMachines} variant="outlined">Retry</Button>
          </>
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

            {/* <Typography variant="h6" sx={{ marginTop: 2 }}>
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
            </List> */}
          </Box>
        )}
      </Paper>
    </Box>
  );
}


export default Dashboard;
