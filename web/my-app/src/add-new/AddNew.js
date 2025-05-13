import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Card,
  CardContent,
  CardHeader,
  Typography,
  TextField,
  Button,
  Box,
} from "@mui/material";
import axios from "axios";
import config from '../config';

// Reusable submit function
const submitMachine = async (formData: any) => {
  const cancelToken = axios.CancelToken.source();

  try {
    const response = await axios.post(
      `${config.API_URL}/machine`,
      formData,
      {
        cancelToken: cancelToken.token,
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    console.log("Machine submitted:", response.data);
    return response.data;
  } catch (error) {
    if (axios.isCancel(error)) {
      console.log("Request canceled:", error.message);
    } else {
      console.error("Submit failed:", error);
      throw error;
    }
  }

  return () => {
    cancelToken.cancel("Component unmounted");
  };
};

const AddMachine = () => {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [manufacturer, setManufacturer] = useState("");
  const [barcode, setBarcode] = useState("");
  const [error, setError] = useState(null); //string

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    const formData = { name, manufacturer, barcode };

    try {
      await submitMachine(formData);
      navigate("/");
    } catch (err) {
      setError("Failed to submit machine. Please try again.");
    }
  };

  return (
    <Box
      sx={{
        backgroundColor: "#f9fafb",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        p: 0,
        m: 0,
        width: "fit-content",
        mx: "auto",
      }}
    >
      <Card
        sx={{
          width: {
            xs: "100%",
            sm: "100%",
            md: 500,
          },
          maxWidth: 500,
          boxShadow: 3,
        }}
      >
        <CardHeader
          title={
            <Typography variant="h5" component="div" fontWeight="bold">
              Add New Machine
            </Typography>
          }
          sx={{ pb: 0 }}
        />
        <CardContent>
          <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{
              display: "flex",
              flexDirection: "column",
              gap: 3,
              mt: 1,
            }}
          >
            <TextField
              id="name"
              label="Name"
              variant="outlined"
              required
              fullWidth
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            <TextField
              id="manufacturer"
              label="Manufacturer"
              variant="outlined"
              required
              fullWidth
              value={manufacturer}
              onChange={(e) => setManufacturer(e.target.value)}
            />
            <TextField
              id="barcode"
              label="Barcode"
              variant="outlined"
              required
              fullWidth
              value={barcode}
              onChange={(e) => setBarcode(e.target.value)}
            />
            {error && (
              <Typography variant="body2" color="error">
                {error}
              </Typography>
            )}
            <Button
              type="submit"
              variant="contained"
              color="primary"
              size="large"
              fullWidth
              sx={{ textTransform: "none", fontWeight: "bold" }}
            >
              Submit
            </Button>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default AddMachine;
