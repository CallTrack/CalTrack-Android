package com.capstone.caltrack.ui.food

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.caltrack.R
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.data.local.entity.FoodEntity
import com.capstone.caltrack.databinding.FragmentFoodSearchBinding
import com.capstone.caltrack.ui.ViewModelFactory
import com.capstone.caltrack.ui.addcalorie.AddCalorieActivity
import java.text.SimpleDateFormat
import java.util.*

class FoodSearchFragment : Fragment() {

    private var listSelectedFood = mutableListOf<FoodEntity>()
    private lateinit var foodViewModel: FoodViewModel
    private var _binding: FragmentFoodSearchBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodSearchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealTime = arguments?.getString(ARG_TIME)
        setUpViewModel()

        foodViewModel.getFood(mealTime!!).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setFoodList(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(context, getString(R.string.data_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        foodViewModel.food.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setFoodList(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(context, getString(R.string.data_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val searchView = binding?.searchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                foodViewModel.searchFood(query, mealTime)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setFoodList(food: List<FoodEntity>) {
        val foodAdapter = FoodSearchAdapter(food)
        binding?.rvFood?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = foodAdapter
        }

        foodAdapter.setOnItemClickCallback(object : FoodSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FoodEntity) {
                if (listSelectedFood.contains(data)) {
                    listSelectedFood.remove(data)
                } else {
                    listSelectedFood.add(data)
                }
                showButton(listSelectedFood.isNotEmpty())
            }
        })
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        foodViewModel = ViewModelProvider(requireActivity(), factory).get(FoodViewModel::class.java)
    }

    private fun showButton(show: Boolean) {
        binding?.button?.isVisible = show
        binding?.button?.setOnClickListener {
            foodViewModel.addMeal(listSelectedFood)
            val calorie = listSelectedFood.sumOf { calorie -> calorie.calories }
            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = simpleDateFormat.format(calendar.time).toString()
            foodViewModel.updateRecord(currentDate, calorie)
            val intent = Intent(activity, AddCalorieActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun showLoading(show: Boolean) {
        binding?.pbLoading?.isVisible = show
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TIME = "meal_time"
    }
}