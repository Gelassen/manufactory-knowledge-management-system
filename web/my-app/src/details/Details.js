import React, { useState, useEffect } from 'react';
import {
  Typography,
  Box,
  Paper,
  CircularProgress,
  List,
  ListItem,
  ListItemText,
  Pagination,
  Divider,
  IconButton,
} from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import EditIcon from '@mui/icons-material/Edit';
import { format } from 'date-fns';
import config from '../config';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function MachineDetails() {
  const navigate = useNavigate()
  const { id } = useParams();

  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [breakdowns, setBreakdowns] = useState([]);
  const [breakdownPage, setBreakdownPage] = useState(1);
  const [totalBreakdownPages, setTotalBreakdownPages] = useState(1);
  const pageSize = 3;

  const fetchMachine = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${config.API_URL}/machine/${id}`);
      setMachine(response.data.data);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch machine data');
      setLoading(false);
    }
  };

  const fetchBreakdowns = async (page = 1) => {
    try {
      const response = await axios.get(`${config.API_URL}/machine/${id}/breakdowns`, {
        params: { page: page - 1, size: pageSize },
      });

      const { data, total } = response.data.data;

      setBreakdowns(data);
      setTotalBreakdownPages(Math.ceil(total / pageSize));
    } catch (err) {
      console.error(err);
      setError('Failed to fetch breakdowns');
    }
  };

  const onAddNewClick = (machine) => {
    navigate(`/machines/${machine.id}/breakdowns`);
  };

  const onEditClick = (machine, breakdown) => {
    navigate(`/machines/${machine.id}/breakdowns/edit/${breakdown.id}`, {
      state: { breakdown },
    });
  }

  useEffect(() => {
    fetchMachine();
    fetchBreakdowns(breakdownPage);
  }, [id, breakdownPage]);

  return (
    <Box display="flex" justifyContent="center" p={2}>
      <Paper sx={{ 
        padding: 4, 
        width: '100%', 
        maxWidth: 700,
        position: 'relative',  
        }}>

        <Typography variant="h4" gutterBottom>
          Machine Details
        </Typography>

        <IconButton
          sx={{ position: 'absolute', top: 24, right: 32 }}
          size="small"
          onClick={(e) => {
            e.stopPropagation();
            onAddNewClick(machine);
          }}
        >
          <AddCircleOutlineIcon />
        </IconButton>

        {loading && !machine && (
          <Box display="flex" justifyContent="center" mb={2}>
            <CircularProgress />
          </Box>
        )}

        {error && (
          <Typography variant="body1" color="error" mb={2}>
            {error}
          </Typography>
        )}

        {machine && (
          <>
            <Typography variant="h6" gutterBottom>
              {machine.name}
            </Typography>
            <Typography variant="body1">
              <strong>Manufacturer:</strong> {machine.manufacturer}
            </Typography>
            <Typography variant="body1" mb={2}>
              <strong>Barcode:</strong> {machine.barcode}
            </Typography>

            <Divider sx={{ my: 2 }} />

            <Typography variant="h6" gutterBottom>
              Breakdowns
            </Typography>
          

            {breakdowns.length > 0 ? (
              <>
                <List>
                  {breakdowns.map((breakdown) => (
                    <ListItem
                      key={breakdown.id}
                      sx={{
                        mb: 2,
                        p: 2,
                        border: '1px solid #e0e0e0',
                        borderRadius: 2,
                        bgcolor: 'background.paper',
                        boxShadow: 1,
                        flexDirection: 'column',
                        alignItems: 'flex-start',
                        position: 'relative'
                      }}
                    >
                      <Typography variant="subtitle1" fontWeight="bold">
                        {breakdown.failure}
                      </Typography>

                      <IconButton
                        sx={{ position: 'absolute', top: 8, right: 8 }}
                        size="small"
                        onClick={(e) => {
                          e.stopPropagation();
                          onEditClick(machine, breakdown);
                        }}
                      >
                        <EditIcon />
                      </IconButton>

                      <Typography variant="body2" sx={{ mt: 1 }}>
                        <strong>Solution:</strong> {breakdown.solution}
                      </Typography>
                      <Typography variant="caption" sx={{ mt: 1 }}>
                        Date: {format(new Date(breakdown.dateTime), 'yyyy-MM-dd HH:mm')}
                      </Typography>
                    </ListItem>
                  ))}
                </List>

                <Box display="flex" justifyContent="center" mt={2}>
                  <Pagination
                    count={totalBreakdownPages}
                    page={breakdownPage}
                    onChange={(_, page) => setBreakdownPage(page)}
                    color="primary"
                  />
                </Box>
              </>
            ) : (
              <Typography variant="body1">No breakdowns available.</Typography>
            )}
          </>
        )}
      </Paper>
    </Box>
  );
}

export default MachineDetails;
