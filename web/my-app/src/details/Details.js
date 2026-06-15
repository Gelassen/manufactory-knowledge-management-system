import React, { useState, useEffect } from 'react';
import {
  Typography,
  Box,
  Paper,
  CircularProgress,
  List,
  ListItem,
  Pagination,
  Divider,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
} from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import EditIcon from '@mui/icons-material/Edit';
import QrCodeIcon from '@mui/icons-material/QrCode';
import { QRCodeCanvas } from 'qrcode.react';
import { format } from 'date-fns';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import client from '../client';

function MachineDetails() {
  alert("console.log([MachineDetails] mounted);")
  
  const navigate = useNavigate();
  const { id } = useParams();

  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [breakdowns, setBreakdowns] = useState([]);
  const [breakdownPage, setBreakdownPage] = useState(1);
  const [totalBreakdownPages, setTotalBreakdownPages] = useState(1);
  const pageSize = 3;

  const [qrOpen, setQrOpen] = useState(false);
  const [qrValue, setQrValue] = useState(null);
  const [qrLoading, setQrLoading] = useState(false);

  const fetchMachine = async () => {
    try {
      setLoading(true);
      const response = await client.get(`/machine/${id}`);
      setMachine(response.data.data);
    } catch (err) {
      setError('Failed to fetch machine data');
    } finally {
      setLoading(false);
    }
  };

  const fetchBreakdowns = async (page = 1) => {
    try {
      const response = await client.get(`/machine/${id}/breakdowns`, {
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

  const onAddNewClick = () => {
    if (machine) {
      navigate(`/machines/${machine.id}/breakdowns`);
    }
  };

  const onShowQrCodeClick = async () => {
    try {
      setQrLoading(true);
      setQrValue(null);
      setError(null);

      const response = await client.get(`/machine/${id}/qr`);
      const qrValue = response?.data?.data?.qrValue;

      if (!qrValue) throw new Error('QR value is missing');

      setQrValue(qrValue);
      setQrOpen(true);
    } catch (err) {
      console.error(err);
      setError('Server error. QR code is not available.');
      setQrOpen(true);
    } finally {
      setQrLoading(false);
    }
  };

  const onEditClick = (breakdown) => {
    if (machine) {
      navigate(`/machines/${machine.id}/breakdowns/edit/${breakdown.id}`, {
        state: { breakdown },
      });
    }
  };

  // Экспонируем функции для AppBar
  useEffect(() => {
    window.showQrCode = onShowQrCodeClick;
    window.addNewBreakdown = onAddNewClick;

    return () => {
      delete window.showQrCode;
      delete window.addNewBreakdown;
    };
  }, [machine]);

  useEffect(() => {
    fetchMachine();
    fetchBreakdowns(breakdownPage);
  }, [id, breakdownPage]);

  return (
    <Box sx={{ maxWidth: 700, mx: 'auto' }}>
      <Paper sx={{ padding: { xs: 2, sm: 3, md: 4 } }}>
        {loading && !machine && (
          <Box display="flex" justifyContent="center" my={4}>
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
            <Typography variant="h5" gutterBottom>
              {machine.name}
            </Typography>

            <Typography variant="body1">
              <strong>Manufacturer:</strong> {machine.manufacturer}
            </Typography>
            <Typography variant="body1" mb={3}>
              <strong>Barcode:</strong> {machine.barcode}
            </Typography>

            <Divider sx={{ my: 3 }} />

            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
              <Typography variant="h6">Breakdowns</Typography>
              <IconButton color="primary" onClick={onAddNewClick}>
                <AddCircleOutlineIcon />
              </IconButton>
            </Box>

            {breakdowns.length > 0 ? (
              <>
                <List>
                  {breakdowns.map((breakdown) => (
                    <ListItem
                      key={breakdown.id}
                      sx={{
                        mb: 2,
                        p: 2.5,
                        border: '1px solid #e0e0e0',
                        borderRadius: 2,
                        bgcolor: 'background.paper',
                        boxShadow: 1,
                        flexDirection: 'column',
                        alignItems: 'flex-start',
                        position: 'relative',
                        minHeight: 110,
                      }}
                    >
                      <Typography variant="subtitle1" fontWeight="bold" pr={5}>
                        {breakdown.failure}
                      </Typography>

                      <IconButton
                        sx={{
                          position: 'absolute',
                          top: 12,
                          right: 12,
                          backgroundColor: 'background.paper',
                          boxShadow: 1,
                        }}
                        size="small"
                        onClick={() => onEditClick(breakdown)}
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

                <Box display="flex" justifyContent="center" mt={3}>
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

      {/* QR Code Dialog */}
      <Dialog open={qrOpen} onClose={() => setQrOpen(false)} maxWidth="xs" fullWidth>
        <DialogTitle>Machine QR Code</DialogTitle>
        <DialogContent
          sx={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            minHeight: 320,
            flexDirection: 'column',
            gap: 2,
          }}
        >
          {qrLoading ? (
            <CircularProgress />
          ) : error ? (
            <Typography variant="body1" color="error" textAlign="center">
              Server error. QR code is not available.
            </Typography>
          ) : qrValue ? (
            <QRCodeCanvas
              value={qrValue}
              size={Math.min(window.innerWidth * 0.7, 260)}
              level="M"
            />
          ) : (
            <Typography variant="body2">QR code is not available.</Typography>
          )}
        </DialogContent>
      </Dialog>
    </Box>
  );
}

export default MachineDetails;