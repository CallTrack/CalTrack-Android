package com.capstone.caltrack.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.caltrack.databinding.FragmentMainBinding
import com.capstone.caltrack.ui.ExerciseActivity
import com.capstone.caltrack.ui.food.FoodActivity
import com.capstone.caltrack.ui.adapter.DrinkAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private var qty = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.icRestaurant?.setOnClickListener {
            val intent = Intent(activity, FoodActivity::class.java)
            startActivity(intent)
        }
        binding?.icBurn?.setOnClickListener {
            val intent = Intent(activity, ExerciseActivity::class.java)
            startActivity(intent)
        }


        binding?.ivAddDrink?.setOnClickListener {
            qty += 1
            setUpDrink()
        }
        binding?.ivRmvDrink?.setOnClickListener {
            if (qty >= 1) {
                qty -= 1
                setUpDrink()
            }
        }

        setUpDrink()
    }

    private fun setUpDrink() {
        binding?.rvDrink?.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = DrinkAdapter(qty)
        }
        binding?.tvDrink?.text = "$qty"
    }

}