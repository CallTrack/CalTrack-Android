package com.capstone.caltrack.ui.censorresult

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.caltrack.R
import com.capstone.caltrack.createFile
import com.capstone.caltrack.databinding.ActivityCameraBinding
import com.capstone.caltrack.showSnackbar
import com.capstone.caltrack.uriToFile

class CameraActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        initGallery()
        binding.let {
            it.btnShutter.setOnClickListener {
                takePhoto()
            }
            it.btnSwitch.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }
            it.btnGallery.setOnClickListener {
                startGallery()
            }
            it.btnBack.setOnClickListener {
                onBackPressed()
            }
        }
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(480, 720))
                .build()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().setTargetResolution(Size(480, 720)).build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture, imageAnalysis
                )
            } catch (e: Exception) {
                showSnackbar(binding.root, getString(R.string.camera_fail))
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun initGallery() {
        openGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                val myFile = uriToFile(selectedImg, this@CameraActivity)
                val intent = Intent(this@CameraActivity, CensorResultActivity::class.java)
                intent.putExtra(CensorResultActivity.EXTRA_PHOTO_RESULT, myFile)
                intent.putExtra(
                    CensorResultActivity.EXTRA_CAMERA_MODE,
                    cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                )
                intent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
                startActivity(intent)
                this@CameraActivity.finish()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        openGalleryLauncher.launch(chooser)
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    showSnackbar(binding.root, getString(R.string.picture_fail))
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent(this@CameraActivity, CensorResultActivity::class.java)
                    intent.putExtra(CensorResultActivity.EXTRA_PHOTO_RESULT, photoFile)
                    intent.putExtra(
                        CensorResultActivity.EXTRA_CAMERA_MODE,
                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
                    startActivity(intent)
                    this@CameraActivity.finish()
                }
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}