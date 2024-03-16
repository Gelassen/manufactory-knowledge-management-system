package io.github.gelassen.manufactory_knowledge_management.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Ref. https://github.com/android/camera-samples/blob/main/CameraX-MLKit/app/src/main/java/com/example/camerax_mlkit/MainActivity.kt
 *
 * (c) Apache License 2.0
 * */
class ScannerFragment: Fragment() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var barcodeScanner: BarcodeScanner
    private var isNavigationTriggeredAlready = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(requireActivity(),
                    "Permissions are not granted by the user.",
                    Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return layoutInflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareScanner()
        cameraExecutor = Executors.newSingleThreadExecutor()
        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        barcodeScanner.close()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireActivity(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun prepareScanner() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)
    }

    private fun startCamera() {
        val cameraController = LifecycleCameraController(requireActivity())
        val previewView: PreviewView = requireView().findViewById(R.id.viewFinder)

        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(requireActivity()),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(requireActivity())
            ) { result: MlKitAnalyzer.Result? ->
                val barcodeResults = result?.getValue(barcodeScanner)
                if ((barcodeResults == null) ||
                    (barcodeResults.size == 0) ||
                    (barcodeResults.first() == null)
                ) {
                    previewView.overlay.clear()
                    previewView.setOnTouchListener { _, _ -> false } //no-op
                    return@MlKitAnalyzer
                }

                onSuccessfulBarcodeScan(barcodeResults)
            }
        )

        cameraController.bindToLifecycle(this)
        previewView.controller = cameraController
    }

    private fun onSuccessfulBarcodeScan(barcodeResults: MutableList<Barcode>) {
        Log.d(App.TAG, "[start] onSuccessfulBarcodeScan(${barcodeResults[0].rawValue})")
        Toast.makeText(
            requireActivity(),
            "QR code is scanned ${barcodeResults[0].rawValue}",
            Toast.LENGTH_SHORT
        )
            .show()
        setFragmentResult(
            MachineFragment.EXTRA_MACHINE_ID,
            bundleOf(MachineFragment.MACHINE_ID to barcodeResults[0].rawValue)
        )
        if (!isNavigationTriggeredAlready) {
            isNavigationTriggeredAlready = true
            val navController = requireActivity().findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.action_scanner_to_machines)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                REQUIRED_PERMISSION
            ).toTypedArray()
    }
}