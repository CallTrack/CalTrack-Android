package com.capstone.caltrack.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.caltrack.R
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.data.local.entity.RecordEntity
import com.capstone.caltrack.databinding.FragmentMainBinding
import com.capstone.caltrack.ui.ViewModelFactory
import com.capstone.caltrack.ui.exercise.ExerciseActivity
import com.capstone.caltrack.ui.adapter.DrinkAdapter
import com.capstone.caltrack.ui.addcalorie.AddCalorieActivity
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var currentDate: String
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireActivity())
        setUpViewModel()
        startUp()

        binding?.frameRestaurant?.setOnClickListener {
            val intent = Intent(activity, AddCalorieActivity::class.java)
            startActivity(intent)
        }
        binding?.frameBurn?.setOnClickListener {
            val intent = Intent(activity, ExerciseActivity::class.java)
            startActivity(intent)
        }

        var qty = sessionManager.getDrink()
        binding?.ivAddDrink?.setOnClickListener {
            qty += 1
            setUpDrink(qty)
        }
        binding?.ivRmvDrink?.setOnClickListener {
            if (qty >= 1) {
                qty -= 1
                setUpDrink(qty)
            }
        }
        setUpDrink(qty)
    }

    private fun startUp() {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        currentDate = simpleDateFormat.format(calendar.time).toString()
        viewModel.getRecordRoom().observe(viewLifecycleOwner) { record ->
            if (record != null){
                setUpRecord(record)
            }
        }
        viewModel.getRecordOnline().observe(viewLifecycleOwner) { record ->
            if (record != null) {
                when (record) {
                    is Result.Loading -> {
                    }
                    is Result.Success -> {
                        val data = record.data
                        setUpGraph(data)
                    }
                    is Result.Error -> {
                    }
                }
            }
            else viewModel.addNewRecord(currentDate)
        }
    }

    private fun setUpRecord(data: List<RecordEntity>) {
        if (data.isEmpty()) {
            viewModel.addNewRecord(currentDate)
            startUp()
        } else {
            val latestRecord = data.first()
            updateProgressBar(latestRecord.caloriesTotal)
            binding?.apply {
                tvCalTotal.text = getString(R.string.calorie_val, latestRecord.caloriesTotal)
                tvCal.text = getString(R.string.calorie_val, latestRecord.caloriesIn)
                tvCalBurn.text = getString(R.string.calorie_val, latestRecord.caloriesBurn)
            }
            if (currentDate != latestRecord.date) {
                viewModel.postRecord(latestRecord.date, latestRecord.caloriesIn, latestRecord.caloriesBurn, latestRecord.caloriesTotal)
                viewModel.addNewRecord(currentDate)
            }
        }
    }

    private fun setUpGraph(data: List<RecordEntity>) {
        // ToBeContinued
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)
    }

    private fun setUpDrink(qty: Int) {
        sessionManager.setDrink(qty)
        binding?.rvDrink?.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = DrinkAdapter(qty)
        }
        binding?.tvDrink?.text = "$qty"
    }

    private fun updateProgressBar(calTotal: Int) {
        binding?.progressBar?.apply {
            max = sessionManager.getDailyCalories()
            progress = calTotal
        }
    }

    override fun onResume() {
        startUp()
        super.onResume()
    }
}