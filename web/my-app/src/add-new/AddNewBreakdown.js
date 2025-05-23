import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams, useLocation } from "react-router-dom";
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

const BreakdownForm = () => {
  const navigate = useNavigate();
  const { machineId, id } = useParams(); // `id` is breakdown ID (optional)
  const { state } = useLocation();

  const isEditMode = Boolean(id);

  const [machine, setMachine] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [title, setTitle] = useState("");
  const [failure, setFailure] = useState("");
  const [solution, setSolution] = useState("");

  useEffect(() => {
    if (isEditMode) {
      if (!state?.breakdown) {
        throw new Error("Missing breakdown data in route state.");
      }

      const { title, failure, solution } = state.breakdown;
      setTitle(title);
      setFailure(failure);
      setSolution(solution);
    }
  }, [isEditMode, state]);

  useEffect(() => {
    axios
      .get(`${config.API_URL}/machine/${machineId}`)
      .then((res) => {
        setMachine(res.data.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Failed to load machine:", err);
        setError("Failed to load machine.");
        setLoading(false);
      });
  }, [machineId]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      title,
      failure,
      solution,
      dateTime: Date.now(),
      photofixations: [],
    };

    const request = isEditMode
      ? axios.put(`${config.API_URL}/machine/${machineId}/breakdowns/${id}`, payload)
      : axios.post(`${config.API_URL}/machine/${machineId}/breakdowns`, payload);

    request
      .then(() => {
        navigate(`/machine/${machineId}`);
      })
      .catch((err) => {
        console.error("Submission error:", err);
        setError("Failed to submit breakdown.");
      });
  };

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
          width: { xs: "100%", sm: "100%", md: 500 },
          maxWidth: 500,
          boxShadow: 3,
        }}
      >
        <CardHeader
          title={
            <Typography variant="h5" fontWeight="bold" mb={2}>
              {isEditMode
                ? `Edit Breakdown (ID: ${id}) for ${machine.name}`
                : `Add Breakdown for ${machine.name} (ID: ${machine.id})`}
            </Typography>
          }
          sx={{ pb: 0 }}
        />
        <CardContent>
          <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{ display: "flex", flexDirection: "column", gap: 3, mt: 1 }}
          >
            <TextField
              label="Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
              fullWidth
            />
            <TextField
              label="Failure"
              value={failure}
              onChange={(e) => setFailure(e.target.value)}
              required
              multiline
              rows={4}
              fullWidth
            />
            <TextField
              label="Solution"
              value={solution}
              onChange={(e) => setSolution(e.target.value)}
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
              {isEditMode ? "Update" : "Submit"}
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

export default BreakdownForm;
