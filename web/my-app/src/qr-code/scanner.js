import { useEffect, useRef, useState } from "react";
import { createPortal } from "react-dom";
import {
  Box,
  Typography,
  CircularProgress,
  IconButton,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import * as Html5QrcodeLib from "html5-qrcode";

const BarcodeScanner = ({ onScanSuccess, onClose }) => {
  const scannerRef = useRef(null);
  const scanHandledRef = useRef(false);
  const stoppedRef = useRef(false);

  const [status, setStatus] = useState("requesting-permission");

  const stopScanner = async () => {
    if (stoppedRef.current || !scannerRef.current) return;

    stoppedRef.current = true;

    try {
      await scannerRef.current.stop();
      await scannerRef.current.clear();
    } catch (err) {
      console.warn("Scanner stop warning:", err);
    }

    scannerRef.current = null;
  };

  const handleClose = async () => {
    await stopScanner();
    onClose();
  };

  useEffect(() => {
    document.body.style.overflow = "hidden";
    document.documentElement.style.overflow = "hidden";

    return () => {
      document.body.style.overflow = "";
      document.documentElement.style.overflow = "";
    };
  }, []);

  useEffect(() => {
    let scanner;

    const startScanner = async () => {
      try {
        const devices = await Html5QrcodeLib.Html5Qrcode.getCameras();

        if (!devices || devices.length === 0) {
          setStatus("error");
          return;
        }

        scanner = new Html5QrcodeLib.Html5Qrcode("reader");
        scannerRef.current = scanner;

        await scanner.start(
          { facingMode: "environment" },
          {
            fps: 10,
            qrbox: undefined, // важно: full-screen режим без «окна»
            rememberLastUsedCamera: true,
          },
          async (decodedText) => {
            if (scanHandledRef.current) return;

            scanHandledRef.current = true;

            try {
              await stopScanner();
              onScanSuccess(decodedText);
            } catch (err) {
              console.error("Scan handling failed:", err);
            }
          },
          () => {}
        );

        setStatus("scanning");
      } catch (err) {
        console.error("Camera error:", err);
        setStatus("error");
      }
    };

    startScanner();

    return () => {
      stopScanner();
    };
  }, [onScanSuccess]);

  const ui = (
    <Box sx={styles.overlay}>
      <IconButton onClick={handleClose} sx={styles.closeBtn}>
        <CloseIcon sx={{ color: "white" }} />
      </IconButton>

      <Box id="reader" sx={styles.reader} />

      {status !== "scanning" && (
        <Box sx={styles.placeholder}>
          {status === "requesting-permission" && (
            <>
              <CircularProgress />
              <Typography variant="h5" sx={{ mt: 3, mb: 2 }}>
                Preparing camera...
              </Typography>
              <Typography
                variant="body1"
                sx={{ maxWidth: 320, textAlign: "center", lineHeight: 1.6 }}
              >
                Please allow camera access in your browser.
              </Typography>
            </>
          )}

          {status === "error" && (
            <>
              <Typography variant="h5" sx={{ mb: 2 }}>
                Camera unavailable
              </Typography>
              <Typography
                variant="body1"
                sx={{ maxWidth: 320, textAlign: "center", lineHeight: 1.6 }}
              >
                Please check camera permissions and try again.
              </Typography>
            </>
          )}
        </Box>
      )}
    </Box>
  );

  return createPortal(ui, document.body);
};

const styles = {
  overlay: {
    position: "fixed",
    inset: 0,
    zIndex: 9999,

    background: "rgba(0,0,0,0.92)",

    width: "100%",
    height: "100%",

    overflow: "hidden",
  },

  reader: {
    position: "absolute",
    inset: 0,

    width: "100%",
    height: "100%",

    "& video": {
      width: "100% !important",
      height: "100% !important",
      objectFit: "cover",
    },

    "& canvas": {
      width: "100% !important",
      height: "100% !important",
    },
  },

  placeholder: {
    position: "absolute",
    inset: 0,

    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",

    padding: 4,
    boxSizing: "border-box",

    color: "white",
    textAlign: "center",
    pointerEvents: "none",
  },

  closeBtn: {
    position: "absolute",
    top: 16,
    left: 16,
    zIndex: 10000,
  },
};

export default BarcodeScanner;