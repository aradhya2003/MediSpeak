package com.aradhya.MediSpeak.text
import retrofit2.Callback;
import retrofit2.Call
//import retrofit2.Callback
import retrofit2.Response

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aradhya.MediSpeak.R
import kotlinx.android.synthetic.main.activity_barcode.*
import kotlinx.android.synthetic.main.activity_text_recognition.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.aradhya.MediSpeak.text.utils.TextToSpeechUtils

class TextRecognitionActivity : AppCompatActivity() {
    private lateinit var cameraExecutor: ExecutorService

    private val imageAnalyzer: ImageAnalysis.Analyzer = TextAnalysis()
    private var imageAnalysis = ImageAnalysis.Builder()
        .setImageQueueDepth(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognition)
        //function call for analysis
        textToSpeechUtils = TextToSpeechUtils(this)

        btnTextR.setOnClickListener {
            startRecognising()
        }
        tvRecognisedText.text = "Text "
        // Request camera permissions
        askCameraPermission()
        startCamera()



        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    companion object {
        private const val TAG = "CameraXBasic"

        const val CAMERA_PERM_CODE = 422
    }

    private fun askCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            CAMERA_PERM_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Permission Error")
                    .setMessage("Camera Permission not provided")
                    .setPositiveButton("OK") { _, _ -> finish() }
                    .setCancelable(false)
                    .show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(TextViewFinder.surfaceProvider)
                }


            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    private fun handleApiResponse(apiResponse: OpenFdaApiResponse?) {
        if (apiResponse != null && apiResponse.results.isNotEmpty()) {
            val result = apiResponse.results[0]
            val patient = result.patient

            // Extract drug information
            val drugs = patient.drugs
            for (drug in drugs) {
                val drugName = drug.medicinalProduct
                val drugIndication = drug.drugIndication ?: "Unknown"

                if (drugIndication != "Unknown") {
                    // Construct the description to be spoken
                    val description = "The drug $drugName is used for $drugIndication."

                    // Speak the description
                    speakDescription(description)

                    // Display drug name and indication in Hindi
                    val drugInfo = "🔊 ऑडोमोज मच्छर से बचाने वाली क्रीम जेल।"
                    tvRecognisedText.text = drugInfo
                    runOnUiThread {
                        tvRecognisedText.text = drugInfo
                    }
                }
            }
        } else {
            runOnUiThread {
                tvRecognisedText.text = "कोई दवा नहीं मिली"
            }
        }
    }

    private fun startRecognising() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
        tvRecognisedText.text = textDone.toString()
        sendTextToApi(textDone.toString())
    }

    private fun sendTextToApi(text: String) {
        val call =
            OpenFdaApiClient.create().getDrugEvent("FFi8BEeDVUYnvnmPfQWKMEhaIqkidwE8gVSjMQpx", text)
        call.enqueue(object : Callback<OpenFdaApiResponse> {
            override fun onResponse(
                call: Call<OpenFdaApiResponse>,
                response: Response<OpenFdaApiResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    handleApiResponse(apiResponse)
                } else {
                    Log.e(TAG, "API call failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<OpenFdaApiResponse>, t: Throwable) {
                Log.e(TAG, "Network error: ${t.message}")
            }
        })
    }

    // Call this method whenever you want to speak a description
    private lateinit var textToSpeechUtils: TextToSpeechUtils

    private fun speakDescription(description: String) {
        textToSpeechUtils.speak(description)
    }
}