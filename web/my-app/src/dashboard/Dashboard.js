import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
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
// import MenuIcon from "@mui/icons-material/Menu";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import axios from 'axios';
import config from '../config';     

const Dashboard = () => {

  const navigate = useNavigate();

  const [machines, setMachines] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchMachines = () => {

    setLoading(true);
    setError(null);

    axios
      .get(`${config.API_URL}/machine/all`)
      .then((response) => {
        setMachines(response.data?.data ?? null);
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

  const onMachineClick = (machine) => {
    console.log('Clicked machine:', machine);
    navigate(`/machines/${machine.id}`);
  };
  
  const onAddNewClick = (machine) => {
    console.log('Add new for:', machine);
    navigate(`/machines/${machine.id}/breakdowns`)
  };

  // TODO: add a grid of machines which have open breakdowns -- in repairing state
  //    it supposed to be at the top of list with the rest of machines (paginated)

  // TODO: list of items also should be paginated

  // TODO: add search field to query a single machine by name
  
  return (
    <div>
        <Typography variant="h4" gutterBottom>
          Machines
        </Typography>

        {/* Loading spinner */}
        {loading && (
          <Box display="flex" justifyContent="center" sx={{ mb: 2 }}>
            <CircularProgress />
          </Box>
        )}

        {/* Error message */}
        {error && (
          <>
            <Typography variant="body1" color="error" sx={{ mb: 2 }}>
              {error}
            </Typography>
            <Button onClick={fetchMachines} variant="outlined">
              Retry
            </Button>
          </>
        )}

        {/* List of machines */}
        <List>
          {machines?.map((machine) => (
            <ListItem
              key={machine.id}
              sx={{
                mb: 2,
                p: 2,
                border: '1px solid #ccc',
                borderRadius: 2,
                position: 'relative',
                cursor: 'pointer',
                '&:hover': {
                  backgroundColor: 'action.hover',
                },
              }}
              onClick={() => onMachineClick(machine)}
            >
              {/* Add icon at top-right */}
              <IconButton
                sx={{
                  position: 'absolute',
                  top: 8,
                  right: 8,
                }}
                size="small"
                onClick={(e) => {
                  e.stopPropagation(); // Prevents ListItem's onClick
                  onAddNewClick(machine); // Your logic for add
                }}
              >
                <AddCircleOutlineIcon />
              </IconButton>

              <ListItemText
                primary={machine.name}
                secondary={
                  <>
                    <div><strong>Manufacturer:</strong> {machine.manufacturer}</div>
                    <div><strong>Barcode:</strong> {machine.barcode}</div>
                  </>
                }
                slotProps={{
                  primary: { component: 'div', variant: 'h6' },
                  secondary: { component: 'div' },
                }}
              />

              {/* Breakdown count at bottom-right */}
              <Typography
                variant="caption"
                sx={{
                  position: 'absolute',
                  bottom: 8,
                  right: 12,
                  fontWeight: 'bold',
                }}
              >
                {machine.breakdowns?.length || 0} breakdowns
              </Typography>
            </ListItem>
          ))}
        </List>
    </div>
  );
}


export default Dashboard;
