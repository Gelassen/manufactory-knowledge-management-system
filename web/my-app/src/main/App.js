import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { ThemeProvider, createTheme, styled } from "@mui/material/styles";
import { Button, Menu, MenuItem, Typography } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import Dashboard from "../dashboard/Dashboard";
import AddMachine from "../add-new/AddNew"
import MachineDetails from '../details/Details';

// Create your theme
const theme = createTheme(); 

const Container = styled("div")(({ theme }) => ({
  maxWidth: "40%",               // You can tweak this for responsiveness
  marginLeft: "30%",             // 30% from the left
  marginRight: "30%",            // 30% from the right
  padding: theme.spacing(2),
  boxSizing: "border-box",       // ensures padding doesn’t overflow
}));

const Header = styled("header")(({ theme }) => ({
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  marginBottom: theme.spacing(4),
}));

const Title = styled(Typography)(({ theme }) => ({
  fontSize: "1.5rem",
  fontWeight: 600,
}));

const App = () => {

    // State to control the Menu visibility
    const [anchorEl, setAnchorEl] = useState(null); // Tracks the element to which menu is anchored
    const [open, setOpen] = useState(false); // Tracks whether the menu is open
  
    // Open the menu on button click
    const handleClick = (event) => {

      setAnchorEl(event.currentTarget); // Sets the button as the anchor element
      setOpen(true); // Opens the menu
    };
  
    // Close the menu when clicked outside or on a menu item
    const handleClose = () => {
      setOpen(false); // Closes the menu
    };

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Container>
          <Header>
            <Title variant="h5">
              Manufacture knowledge management system
            </Title>
            <Button 
              variant="outlined" 
              aria-controls="menu" 
              aria-haspopup="true"
              onClick={handleClick} >
              <MenuIcon />
            </Button>
            <Menu
              id="menu"
              anchorEl={anchorEl} // Set the button as the anchor element
              open={open} // Control the visibility of the menu
              onClose={handleClose} // Close the menu when clicked outside
            >
              <MenuItem 
                component={Link} 
                to="/"
                onClick={() => {
                  // setAnchorEl(null); // close the menu
                  handleClose()
                  console.log('Navigated to list');
                }}>
                List of machines
              </MenuItem>

              <MenuItem 
                component={Link} 
                to="/new"
                onClick={() => {
                  // setAnchorEl(null); // close the menu
                  handleClose()
                  console.log('Navigated to list');
                }}>
                Add new machine
              </MenuItem>

            </Menu>
          </Header>

          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/new" element={<AddMachine />} />
            <Route path="/machines/:id" element={<MachineDetails />} />
          </Routes>
        </Container>
      </Router>
    </ThemeProvider>
  );
};

export default App;
