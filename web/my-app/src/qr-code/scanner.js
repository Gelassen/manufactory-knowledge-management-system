import { useEffect, useRef } from "react";
import * as Html5QrcodeLib from "html5-qrcode";

const BarcodeScanner = ({ onScanSuccess, onClose }) => {
  const scannerRef = useRef(null);

  useEffect(() => {
    let scanner;

    const config = {
      fps: 10,
      qrbox: 250,
      rememberLastUsedCamera: true,
    };

    // 1. Получаем список камер (static method)
    Html5QrcodeLib.Html5Qrcode.getCameras()
      .then((devices) => {
        if (!devices || devices.length === 0) {
          console.error("No cameras found");
          return;
        }

        const cameraId = devices[0].id;

        // 2. Создаём ИНСТАНС сканера
        scanner = new Html5QrcodeLib.Html5Qrcode("reader");
        scannerRef.current = scanner;

        // 3. Стартуем сканирование
        return scanner.start(
          cameraId,
          config,
          (decodedText) => {
            // SUCCESS
            scanner
              .stop()
              .then(() => {
                onScanSuccess(decodedText);
              })
              .catch((err) => console.error("Stop error:", err));
          },
          (errorMessage) => {
            // ignore frequent scan errors
            // console.debug(errorMessage);
          }
        );
      })
      .catch((err) => {
        console.error("Camera error:", err);
      });

    // cleanup
    return () => {
      if (scannerRef.current) {
        scannerRef.current
          .stop()
          .catch(() => {})
          .finally(() => {
            scannerRef.current = null;
          });
      }
    };
  }, [onScanSuccess]);

  return (
    <div style={styles.overlay}>
      <div style={styles.container}>
        <div id="reader" style={styles.reader} />
        <button style={styles.closeBtn} onClick={onClose}>
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