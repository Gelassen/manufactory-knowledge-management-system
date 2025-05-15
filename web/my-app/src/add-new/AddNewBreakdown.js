import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import {
  Card,
  CardContent,
  CardHeader,
  Typography,
  TextField,
  Button,
  Box,
} from "@mui/material";
import config from "../config"; 

const AddBreakdown = () => {
  const navigate = useNavigate();
  const { machineId } = useParams(); // expects route like /breakdown/add/:machineId

  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(true);

  const [title, setTitle] = useState("");
  const [failure, setFailure] = useState("");
  const [solution, setSolution] = useState("");
  const [error, setError] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      title,
      failure,
      solution,
      dateTime: Date.now(),
      photofixations: []
    };

    axios
      .post(`${config.API_URL}/machine/${machineId}/breakdowns`, payload)
      .then(() => {
        navigate("/"); // or navigate back to machine page
      })
      .catch((err) => {
        console.error(err);
        setError("Failed to submit breakdown.");
      });
  };

  useEffect(() => {
    axios
      .get(`${config.API_URL}/machine/${machineId}`)
      .then((res) => {
        setMachine(res.data.data); // adjust based on your API response
        setLoading(false);
      })
      .catch((err) => {
        console.error("Failed to load machine:", err);
        setLoading(false);
      });
  }, [machineId]);

  if (loading) return <div>Loading...</div>;

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
            <Typography variant="h5" fontWeight="bold" mb={2}>
                Add Breakdown for {machine.name} (ID: {machine.id})
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
              id="title"
              label="Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              variant="outlined"
              required
              fullWidth
            />
            <TextField
              id="failure"
              label="Failure"
              value={failure}
              onChange={(e) => setFailure(e.target.value)}
              variant="outlined"
              required
              multiline
              rows={4}
              fullWidth
            />
            <TextField
              id="solution"
              label="Solution"
              value={solution}
              onChange={(e) => setSolution(e.target.value)}
              variant="outlined"
              required
              multiline
              rows={4}
              fullWidth
            />
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
            {error && (
              <Typography color="error" variant="body2">
                {error}
              </Typography>
            )}
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default AddBreakdown;
