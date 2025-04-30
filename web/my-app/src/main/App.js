import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { ThemeProvider, createTheme, styled } from "@mui/material/styles";
import { Button, Menu, MenuItem, Typography } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import Dashboard from "../dashboard/Dashboard";
import AddMachine from "../add_new/Add_new"

// Create your theme
const theme = createTheme(); 

const Container = styled("div")(({ theme }) => ({
  padding: theme.spacing(2),
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
              Machines Dashboard
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
              <MenuItem component={Link} to="/">
                List of machines
              </MenuItem>

              <MenuItem component={Link} to="/new">
                Add new machine
              </MenuItem>

            </Menu>
          </Header>

          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/new" element={<AddMachine />} />
          </Routes>
        </Container>
      </Router>
    </ThemeProvider>
  );
};

export default App;


// function MachineDetails() {
//   const [barcode, setBarcode] = useState('');
//   const [machine, setMachine] = useState(null);
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState(null);

//   // Fetch machine data by barcode
//   const fetchMachineData = () => {
//     if (!barcode) {
//       setError('Please enter a barcode');
//       return;
//     }

//     setLoading(true);
//     setError(null);

//     axios
//       .get(`${config.API_URL}/machine?barcode=${barcode}`)
//       .then((response) => {
//         setMachine(response.data.data);
//         setLoading(false);
//       })
//       .catch((error) => {
//         console.error('Error fetching data:', error);
//         setError('Failed to fetch machine data');
//         setLoading(false);
//       });
//   };

//   return (
//     <Box
//       sx={{
//         display: 'flex',
//         justifyContent: 'center',
//         alignItems: 'center',
//         minHeight: '100vh',
//         bgcolor: 'background.default',
//         padding: '20px',
//       }}
//     >
//       <Paper sx={{ padding: 4, width: '100%', maxWidth: 600 }}>
//         <Typography variant="h4" gutterBottom>
//           Machine Details
//         </Typography>

//         <TextField
//           label="Enter machine barcode"
//           variant="outlined"
//           fullWidth
//           value={barcode}
//           onChange={(e) => setBarcode(e.target.value)}
//           sx={{ marginBottom: 2 }}
//         />

//         <Button
//           variant="contained"
//           color="primary"
//           fullWidth
//           onClick={fetchMachineData}
//           sx={{ marginBottom: 2 }}
//         >
//           Fetch Machine
//         </Button>

//         {/* Display loading spinner */}
//         {loading && (
//           <Box display="flex" justifyContent="center" sx={{ marginBottom: 2 }}>
//             <CircularProgress />
//           </Box>
//         )}

//         {/* Display error message */}
//         {error && (
//           <Typography variant="body1" color="error" sx={{ marginBottom: 2 }}>
//             {error}
//           </Typography>
//         )}

//         {/* Display machine data */}
//         {machine && (
//           <Box>
//             <Typography variant="h6" gutterBottom>
//               <strong>Name:</strong> {machine.name}
//             </Typography>
//             <Typography variant="body1">
//               <strong>Manufacturer:</strong> {machine.manufacturer}
//             </Typography>
//             <Typography variant="body1">
//               <strong>Barcode:</strong> {machine.barcode}
//             </Typography>

//             <Typography variant="h6" sx={{ marginTop: 2 }}>
//               Breakdowns:
//             </Typography>
//             <List>
//               {machine.breakdowns && machine.breakdowns.length > 0 ? (
//                 machine.breakdowns.map((breakdown) => (
//                   <ListItem key={breakdown.id}>
//                     <ListItemText
//                       primary={<strong>{breakdown.failure}</strong>}
//                       secondary={
//                         <>
//                           <Typography variant="body2">{breakdown.solution}</Typography>
//                           <Typography variant="body2">
//                             <strong>Date:</strong> {new Date(breakdown.dateTime).toLocaleString()}
//                           </Typography>
//                         </>
//                       }
//                     />
//                   </ListItem>
//                 ))
//               ) : (
//                 <Typography variant="body1">No breakdowns available.</Typography>
//               )}
//             </List>
//           </Box>
//         )}
//       </Paper>
//     </Box>
//   );
// }

// export default MachineDetails;
