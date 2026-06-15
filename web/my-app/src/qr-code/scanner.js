import { useEffect, useRef } from "react";
import * as Html5QrcodeLib from "html5-qrcode";

const BarcodeScanner = ({ onScanSuccess, onClose }) => {
  const scannerRef = useRef(null);

  // QR уже обработан
  const scanHandledRef = useRef(false);

  // scanner уже остановлен
  const stoppedRef = useRef(false);

  useEffect(() => {
    let scanner;

    const config = {
      fps: 10,
      qrbox: 250,
      rememberLastUsedCamera: true,
    };

    const stopScanner = async () => {
      if (
        stoppedRef.current ||
        !scannerRef.current
      ) {
        return;
      }

      stoppedRef.current = true;

      try {
        await scannerRef.current.stop();
      } catch (err) {
        console.warn("Scanner stop warning:", err);
      }

      scannerRef.current = null;
    };

    const startScanner = async () => {
      try {
        const devices =
          await Html5QrcodeLib.Html5Qrcode.getCameras();

        if (!devices || devices.length === 0) {
          console.error("No cameras found");
          return;
        }

        scanner = new Html5QrcodeLib.Html5Qrcode(
          "reader"
        );

        scannerRef.current = scanner;

        await scanner.start(
          {
            facingMode: "environment",
          },
          config,

          async (decodedText) => {
            // предотвращаем повторное чтение
            if (scanHandledRef.current) {
              return;
            }

            scanHandledRef.current = true;

            try {
              await stopScanner();

              onScanSuccess(decodedText);
            } catch (err) {
              console.error(
                "Scan success handling failed:",
                err
              );
            }
          },

          (errorMessage) => {
            // html5-qrcode генерирует много ошибок во время поиска
            // это нормальное поведение

            // console.debug(errorMessage);
          }
        );
      } catch (err) {
        console.error("Camera error:", err);
      }
    };

    startScanner();

    return () => {
      stopScanner();
    };
  }, [onScanSuccess]);

  return (
    <div style={styles.overlay}>
      <div style={styles.container}>
        <div id="reader" style={styles.reader} />

        <button
          style={styles.closeBtn}
          onClick={async () => {
            if (
              scannerRef.current &&
              !stoppedRef.current
            ) {
              try {
                stoppedRef.current = true;
                await scannerRef.current.stop();
              } catch (err) {
                console.warn(err);
              }
            }

            onClose();
          }}
        >
          Close
        </button>
      </div>
    </div>
  );
};

const styles = {
  overlay: {
    position: "fixed",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    background: "rgba(0,0,0,0.8)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 9999,
  },

  container: {
    width: "100%",
    maxWidth: 400,
    background: "#fff",
    padding: 10,
    borderRadius: 8,
  },

  reader: {
    width: "100%",
  },

  closeBtn: {
    marginTop: 10,
    width: "100%",
    padding: 10,
  },
};

export default BarcodeScanner;