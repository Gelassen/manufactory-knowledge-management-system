package io.github.gelassen.manufactory_knowledge_management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions.ZoomCallback
import com.google.mlkit.vision.barcode.common.Barcode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_permission_to_scanner)
    }

    private val zoomCallback = ZoomCallback { false /*zoomLevel -> cameraSource!!.setZoom(zoomLevel)*/  }

    private fun configBarcodeScanner() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
            )
            /*.enableAllPotentialBarcodes() */ // optional for enable zooming
            .setZoomSuggestionOptions(
                ZoomSuggestionOptions.Builder(zoomCallback)
                    /*.setMaxSupportedZoomRatio(maxSupportedZoomRatio)*/ // ref https://developers.google.com/ml-kit/vision/barcode-scanning/android#1.-configure-the-barcode-scanner
                    .build()) // Optional

            .build()
    }
    
}