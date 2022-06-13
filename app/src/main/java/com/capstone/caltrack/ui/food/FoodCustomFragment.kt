package com.capstone.caltrack.ui.food

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.capstone.caltrack.R
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.databinding.FragmentFoodCustomBinding
import com.capstone.caltrack.ui.ViewModelFactory
import com.capstone.caltrack.ui.addcalorie.AddCalorieActivity
import java.text.SimpleDateFormat
import java.util.*

class FoodCustomFragment : Fragment() {

    private lateinit var foodViewModel: FoodViewModel
    private var _binding: FragmentFoodCustomBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodCustomBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealTime = arguments?.getString(FoodSearchFragment.ARG_TIME)
        setUpViewModel()

        binding?.button?.setOnClickListener {
            binding?.apply {
                val food = edtFood.text.toString()
                val cal = edtCal.text.toString()
                when {
                    food.isEmpty() -> binding?.foodEditTextLayout?.error = getString(R.string.please_insert)
                    cal.isEmpty() -> binding?.caloriesEditTextLayout?.error = getString(R.string.please_insert)
                    else -> {
                        val foodEntity = FoodEntity(1000, food, cal.toInt(), false, mealTime)
                        val foodEntityList = mutableListOf<FoodEntity>()
                        foodEntityList.add(foodEntity)
                        foodViewModel.addMeal(foodEntityList)
                        val calendar = Calendar.getInstance()
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val currentDate = simpleDateFormat.format(calendar.time).toString()
                        foodViewModel.updateRecord(currentDate, cal.toInt())
                        val intent = Intent(activity, AddCalorieActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        foodViewModel = ViewModelProvider(requireActivity(), factory).get(FoodViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TIME = "meal_time"
    }
}