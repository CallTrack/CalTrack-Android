package com.capstone.caltrack.ui.addcalorie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.databinding.ActivityAddCalorieBinding
import com.capstone.caltrack.ui.ViewModelFactory
import com.capstone.caltrack.ui.food.FoodActivity

class AddCalorieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCalorieBinding
    private lateinit var addCalorieViewModel: AddCalorieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCalorieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Daily Meal"
        setUpViewModel()

        binding.apply {
            icAdd32Breakfast.setOnClickListener { addFoodIntent("breakfast") }
            icAdd32Lunch.setOnClickListener { addFoodIntent("lunch") }
            icAdd32Dinner.setOnClickListener { addFoodIntent("dinner") }
            icAdd32Snack.setOnClickListener { addFoodIntent("snack") }
        }
    }

    private fun setUpViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        addCalorieViewModel = ViewModelProvider(this, factory).get(AddCalorieViewModel::class.java)
        addCalorieViewModel.getMeal("breakfast").observe(this) { foodList ->
            setUpRecyclerView(foodList, "breakfast")
        }
        addCalorieViewModel.getMeal("lunch").observe(this) { foodList ->
            setUpRecyclerView(foodList, "lunch")
        }
        addCalorieViewModel.getMeal("dinner").observe(this) { foodList ->
            setUpRecyclerView(foodList, "dinner")
        }
        addCalorieViewModel.getMeal("snack").observe(this) { foodList ->
            setUpRecyclerView(foodList, "snack")
        }
    }

    private fun setUpRecyclerView(foodList: List<FoodEntity>, time: String) {
        val manager = LinearLayoutManager(this)
        val adapter = FoodAdapter(foodList)
        when (time) {
            "breakfast" -> {
                binding.rvBreakfast.layoutManager = manager
                binding.rvBreakfast.adapter = adapter
            }
            "lunch" -> {
                binding.rvLunch.layoutManager = manager
                binding.rvLunch.adapter = adapter
            }
            "dinner" -> {
                binding.rvDinner.layoutManager = manager
                binding.rvDinner.adapter = adapter
            }
            "snack" -> {
                binding.rvSnack.layoutManager = manager
                binding.rvSnack.adapter = adapter
            }
        }
    }

    private fun addFoodIntent(time: String) {
        val intent = Intent(this, FoodActivity::class.java)
        intent.putExtra(FoodActivity.EXTRA_TIME, time)
        startActivity(intent)
    }
}