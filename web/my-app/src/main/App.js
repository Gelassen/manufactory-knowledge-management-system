import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from "react-router-dom";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Menu,
  MenuItem,
  Container,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

import Dashboard from "../dashboard/Dashboard";
import AddMachine from "../add-new/AddNew";
import MachineDetails from '../details/Details';
import AddBreakdown from '../add-new/AddNewBreakdown';

const theme = createTheme();

// Вынесли Header в отдельный компонент, чтобы использовать useLocation внутри Router
const AppHeader = () => {
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const location = useLocation();

  const isDetailsPage = /^\/machines\/\d+$/.test(location.pathname);
  const machineId = isDetailsPage ? location.pathname.split('/')[2] : null;

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <AppBar position="static" color="default" elevation={1}>
      <Toolbar sx={{ minHeight: { xs: 56, sm: 64 } }}>
        {/* Кнопка Назад только на странице деталей */}
        {isDetailsPage && (
          <IconButton
            edge="start"
            color="inherit"
            onClick={() => window.history.back()}
            sx={{ mr: 2 }}
          >
            <ArrowBackIcon />
          </IconButton>
        )}

        <Typography 
          variant="h6" 
          component="div" 
          sx={{ 
            flexGrow: 1,
            fontWeight: 600,
            whiteSpace: 'nowrap',
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            fontSize: { xs: '1.1rem', sm: '1.25rem' }
          }}
        >
          {isDetailsPage 
            ? `Machine #${machineId}` 
            : "Manufacture Knowledge System"}
        </Typography>

        <IconButton
          edge="end"
          color="inherit"
          onClick={handleMenuClick}
        >
          <MenuIcon />
        </IconButton>

        <Menu
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
        >
          <MenuItem component={Link} to="/" onClick={handleClose}>
            List of machines
          </MenuItem>
          <MenuItem component={Link} to="/machines/new" onClick={handleClose}>
            Add new machine
          </MenuItem>
        </Menu>
      </Toolbar>
    </AppBar>
  );
};

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <Router>
        <AppHeader />

        <Container 
          maxWidth="lg" 
          sx={{ 
            px: { xs: 2, sm: 3, md: 4 },
            py: { xs: 2, sm: 3 },
            boxSizing: 'border-box',
            minHeight: 'calc(100vh - 64px)',
          }}
        >
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/machines/new" element={<AddMachine />} />
            <Route path="/machines/:machineId/breakdowns/edit/:id" element={<AddBreakdown />} />
            <Route path="/machines/:machineId/breakdowns" element={<AddBreakdown />} />
            <Route path="/machines/:id" element={<MachineDetails />} />
          </Routes>
        </Container>
      </Router>
    </ThemeProvider>
  );
};

export default App;