package com.capstone.caltrack.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.caltrack.R
import com.capstone.caltrack.data.local.SessionManager
import com.capstone.caltrack.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var sessionManager: SessionManager
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireActivity())

        binding?.apply {
            valueHeight.text = sessionManager.getHeight()
            valueWeight.text = sessionManager.getWeight()
            valueActivityLevel.text = sessionManager.getActive()
        }
    }
}