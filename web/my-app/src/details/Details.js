import React, { useState, useEffect, useMemo } from 'react';
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
import { QRCodeCanvas } from 'qrcode.react';
import { format } from 'date-fns';
import { useParams, useNavigate } from 'react-router-dom';
import client from '../client';

function MachineDetails({ setHeaderActions }) {
  const navigate = useNavigate();
  const { id, barcode } = useParams();

  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [breakdowns, setBreakdowns] = useState([]);
  const [page, setPage] = useState(1);
  const pageSize = 3;

  const [qrOpen, setQrOpen] = useState(false);
  const [qrValue, setQrValue] = useState(null);
  const [qrLoading, setQrLoading] = useState(false);

  // ✔ единый источник истины
  const machineId = machine?.id;

  useEffect(() => {
    if (!machineId) {
      setHeaderActions({
        showQrCode: null,
        addNewBreakdown: null,
      });
      return;
    }

    setHeaderActions({
      showQrCode: onShowQrCodeClick,
      addNewBreakdown: onAddNewClick,
    });

    return () => {
      setHeaderActions({
        showQrCode: null,
        addNewBreakdown: null,
      });
    };
  }, [machineId]);

  /**
   * 1. Load machine (by id OR barcode)
   */
  useEffect(() => {
    const fetchMachine = async () => {
      try {
        setLoading(true);

        let response;

        if (id) {
          response = await client.get(`/machine/${id}`);
        } else if (barcode) {
          response = await client.get(`/machine/barcode/${barcode}`);
        } else {
          throw new Error('No identifier provided');
        }

        setMachine(response.data.data);
      } catch (err) {
        setError('Failed to fetch machine');
      } finally {
        setLoading(false);
      }
    };

    fetchMachine();
  }, [id, barcode]);

  /**
   * 2. Load breakdowns ONLY when machineId is known
   */
  useEffect(() => {
    if (!machineId) return;

    const fetchBreakdowns = async () => {
      try {
        const response = await client.get(
          `/machine/${machineId}/breakdowns`,
          {
            params: { page: page - 1, size: pageSize },
          }
        );

        const { data, total } = response.data.data;

        setBreakdowns(data);
      } catch (err) {
        setError('Failed to fetch breakdowns');
      }
    };

    fetchBreakdowns();
  }, [machineId, page]);

  const onAddNewClick = () => {
    if (!machineId) return;

    navigate(`/machines/${machineId}/breakdowns`);
  };

  const onShowQrCodeClick = async () => {
    if (!machineId) return;

    try {
      setQrLoading(true);
      setQrValue(null);

      const response = await client.get(
        `/machine/${machineId}/qr`
      );

      const value = response?.data?.data?.qrValue;

      if (!value) {
        throw new Error('QR value is missing');
      }

      setQrValue(value);
      setQrOpen(true);
    } catch (err) {
      setError('QR code is not available');
      setQrOpen(true);
    } finally {
      setQrLoading(false);
    }
  };

  /**
   * Navigation actions
   */
  const onEditClick = (b) => {
    navigate(`/machines/${machineId}/breakdowns/edit/${b.id}`, {
      state: { breakdown: b },
    });
  };

  if (loading && !machine) {
    return (
      <Box display="flex" justifyContent="center" mt={5}>
        <CircularProgress />
      </Box>
    );
  }

  if (!machine) {
    return (
      <Typography>
        Machine not found
      </Typography>
    );
  }

  return (
    <Box sx={{ maxWidth: 700, mx: 'auto' }}>
      <Paper sx={{ p: 3 }}>

        {error && (
          <Typography color="error">{error}</Typography>
        )}

        <Typography variant="h5">
          {machine.name}
        </Typography>

        <Typography>
          Manufacturer: {machine.manufacturer}
        </Typography>

        <Typography>
          Barcode: {machine.barcode}
        </Typography>

        <Divider sx={{ my: 2 }} />

        {breakdowns.length === 0 ? (
          <Typography>No breakdowns</Typography>
        ) : (
          <List>
            {breakdowns.map((b) => (
              <ListItem key={b.id}>
                <Box sx={{ width: '100%' }}>
                  <Typography fontWeight="bold">
                    {b.failure}
                  </Typography>

                  <Typography variant="body2">
                    {b.solution}
                  </Typography>

                  <Typography variant="caption">
                    {format(new Date(b.dateTime), 'yyyy-MM-dd HH:mm')}
                  </Typography>

                  <IconButton onClick={() => onEditClick(b)}>
                    <EditIcon />
                  </IconButton>
                </Box>
              </ListItem>
            ))}
          </List>
        )}
      </Paper>

      <Dialog
        open={qrOpen}
        onClose={() => setQrOpen(false)}
        maxWidth="xs"
        fullWidth
      >
        <DialogTitle sx={{textAlign: "center",}}>QR</DialogTitle>

        <DialogContent
          sx={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            p: 4,
          }}
        >
          {qrLoading ? (
            <CircularProgress />
          ) : (
            <QRCodeCanvas
              value={qrValue || ''}
              size={Math.min(window.innerWidth * 0.5, 300)}
            />
          )}
        </DialogContent>
      </Dialog>
    </Box>
  );
}

export default MachineDetails;