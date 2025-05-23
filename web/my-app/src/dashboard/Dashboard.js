import { useState, useEffect } from 'react';
import {
  Typography,
  CircularProgress,
  Box,
  List,
  ListItem,
  ListItemText,
  IconButton,
  Button,
  Pagination,
  TextField,
  InputAdornment,
} from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import SearchIcon from '@mui/icons-material/Search';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import config from '../config';

const Dashboard = () => {
  const navigate = useNavigate();

  const [machines, setMachines] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [page, setPage] = useState(1);
  const [pageSize] = useState(2);
  const [totalPages, setTotalPages] = useState(1);

  const [searchText, setSearchText] = useState('');
  const [searchTextInput, setSearchTextInput] = useState('');

  const fetchMachines = (pageNum = 1, text = '') => {
    setLoading(true);
    setError(null);

    axios
      .get(`${config.API_URL}/machine/all`, {
        params: {
          page: pageNum - 1,
          size: pageSize,
          text: text.trim() !== '' ? text : undefined,
        },
      })
      .then((response) => {
        setMachines(response.data?.data?.data ?? []);
        setTotalPages(Math.ceil((response.data.data.total || 0) / pageSize));
        setLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setError('Failed to fetch machine data');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchMachines(page, searchText);
  }, [page, searchText]);

  // Debounced search
  useEffect(() => {
    const delayDebounce = setTimeout(() => {
      setPage(1); // Reset to first page on new search input
      setSearchText(searchTextInput);
    }, 300); // 300ms debounce

    return () => clearTimeout(delayDebounce); // Cancel if user types again
  }, [searchTextInput]);

  const onMachineClick = (machine) => {
    navigate(`/machines/${machine.id}`);
  };

  const onAddNewClick = (machine) => {
    navigate(`/machines/${machine.id}/breakdowns`);
  };

  const handlePageChange = (_, value) => {
    setPage(value);
  };

  return (
    <div>
      {/* Debounced Search Field */}
      <Box 
        display="flex" 
        alignItems="center" 
        mb={2} 
        gap={2}
        width="100%"
        >
        <TextField
          label="Search machines"
          variant="outlined"
          value={searchTextInput}
          onChange={(e) => setSearchTextInput(e.target.value)}
          size="small"
          sx={{ width: '100%' }}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
        />
      </Box>

      <Typography variant="h4" gutterBottom>
        Machines
      </Typography>

      {loading && (
        <Box display="flex" justifyContent="center" sx={{ mb: 2 }}>
          <CircularProgress />
        </Box>
      )}

      {error && (
        <>
          <Typography variant="body1" color="error" sx={{ mb: 2 }}>
            {error}
          </Typography>
          <Button onClick={() => fetchMachines(page, searchText)} variant="outlined">
            Retry
          </Button>
        </>
      )}

      <List>
        {machines.map((machine) => (
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
            <IconButton
              sx={{ position: 'absolute', top: 8, right: 8 }}
              size="small"
              onClick={(e) => {
                e.stopPropagation();
                onAddNewClick(machine);
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

      <Box display="flex" justifyContent="center" mt={4}>
        <Pagination
          count={totalPages}
          page={page}
          onChange={handlePageChange}
          color="primary"
        />
      </Box>
    </div>
  );
};

export default Dashboard;
