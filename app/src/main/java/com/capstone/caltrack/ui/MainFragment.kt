package com.capstone.caltrack.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.caltrack.databinding.FragmentMainBinding

class MainFragment : Fragment() {

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

        binding?.icRestaurant?.setOnClickListener {
            val intent = Intent(activity, FoodActivity::class.java)
            startActivity(intent)
        }
        binding?.icBurn?.setOnClickListener {
            val intent = Intent(activity, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }

}