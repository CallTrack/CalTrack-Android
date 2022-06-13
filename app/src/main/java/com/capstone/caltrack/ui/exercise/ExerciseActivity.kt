package com.capstone.caltrack.ui.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.caltrack.R
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.data.local.entity.ExerciseEntity
import com.capstone.caltrack.databinding.ActivityExerciseBinding
import com.capstone.caltrack.ui.ViewModelFactory

class ExerciseActivity : AppCompatActivity() {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var binding: ActivityExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()

        supportActionBar?.title = "Exercise"

        viewModel.getExercise().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        val data = result.data
                        setUpRecyclerView(data)
                    }
                    is Result.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(this, getString(R.string.data_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    private fun setUpRecyclerView(data: List<ExerciseEntity>) {
        val exerciseAdapter = ExerciseAdapter{ exercise ->
            if (exercise.isBookmarked) {
                viewModel.deleteExercise(exercise)
            }
            else {
                viewModel.saveExercise(exercise)
            }
        }
        binding.rvExercise.apply {
            layoutManager = LinearLayoutManager(this@ExerciseActivity)
            setHasFixedSize(true)
            adapter = exerciseAdapter
        }
        exerciseAdapter.submitList(data)
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ExerciseViewModel::class.java)
    }
}