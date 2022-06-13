package com.capstone.caltrack.ui.censorresult

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.capstone.caltrack.R
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.data.remote.response.Food
import com.capstone.caltrack.databinding.ActivityCensorResultBinding
import com.capstone.caltrack.rotateBitmap
import com.capstone.caltrack.ui.ViewModelFactory
import com.capstone.caltrack.ui.main.MainActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CensorResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCensorResultBinding
    private lateinit var viewModel: CameraViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCensorResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Result"

        setUpViewModel()
        sessionManager = SessionManager(this)

        val myFile = intent?.getSerializableExtra(EXTRA_PHOTO_RESULT) as File
        val isBackCamera = intent?.getBooleanExtra(EXTRA_CAMERA_MODE, true) as Boolean
        val result = intent.getParcelableExtra<Food>("Result") as Food
        val rotatedBitmap = rotateBitmap(
            BitmapFactory.decodeFile(myFile.path),
            isBackCamera
        )

        binding.apply {
            tvFood.text = result.name
            tvCalServ.text = getString(R.string.calorie_val, result.calories)
        }

        binding.btnAddFood.setOnClickListener {
            val checkedId = binding.rgOption.checkedRadioButtonId
            val mealTime: RadioButton = findViewById(checkedId)
            val foodEntity = FoodEntity(result.idFood, result.name, result.calories, false, mealTime.text.toString().lowercase())
            var foodEntityList = mutableListOf<FoodEntity>()
            foodEntityList.add(foodEntity)
            viewModel.addMeal(foodEntityList)

            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = simpleDateFormat.format(calendar.time).toString()
            viewModel.updateRecord(currentDate, foodEntity.calories)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.ivResult.setImageBitmap(rotatedBitmap)
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(CameraViewModel::class.java)
    }

    companion object {
        const val EXTRA_PHOTO_RESULT = "PHOTO_RESULT"
        const val EXTRA_CAMERA_MODE = "CAMERA_MODE"
    }
}