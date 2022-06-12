package com.capstone.caltrack.ui.censorresult

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.caltrack.R
import com.capstone.caltrack.databinding.ActivityCensorResultBinding
import com.capstone.caltrack.rotateBitmap
import java.io.File

class CensorResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCensorResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCensorResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myFile = intent?.getSerializableExtra(EXTRA_PHOTO_RESULT) as File
        val isBackCamera = intent?.getBooleanExtra(EXTRA_CAMERA_MODE, true) as Boolean
        val rotatedBitmap = rotateBitmap(
            BitmapFactory.decodeFile(myFile.path),
            isBackCamera
        )
        binding.ivResult.setImageBitmap(rotatedBitmap)
    }

    companion object {
        const val EXTRA_PHOTO_RESULT = "PHOTO_RESULT"
        const val EXTRA_CAMERA_MODE = "CAMERA_MODE"
    }
}