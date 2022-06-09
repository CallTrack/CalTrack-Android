package com.capstone.caltrack.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.caltrack.R
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.databinding.ActivityRegisterBinding
import com.capstone.caltrack.ui.login.LoginActivity
import com.capstone.caltrack.ui.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setUpViewModel()
        dropdownItem()
        setUpAction()
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }

    private fun dropdownItem(){
        val gender = resources.getStringArray(R.array.Gender)
        val arrayGender = ArrayAdapter(this, R.layout.dropdown_item, gender)
        binding.etGender.setAdapter(arrayGender)

        val activity = resources.getStringArray(R.array.Activity)
        val arrayActivity = ArrayAdapter(this, R.layout.dropdown_item, activity)
        binding.etActivity.setAdapter(arrayActivity)
    }

    private fun setUpAction() {
        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val age = binding.etAge.text.toString()
            val gender = binding.etGender.text.toString()
            val height = binding.etHeight.text.toString()
            val weight = binding.etWeight.text.toString()
            val activeLevel = binding.etActivity.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "Insert Email"
                }
                name.isEmpty() -> {
                    binding.etEmail.error = "Insert Name"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "Insert Password"
                }
                age.isEmpty() -> {
                    binding.etAge.error = "Insert Age"
                }
                gender.isEmpty() -> {
                    binding.etGender.error = "Insert Gender"
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                registerViewModel.register(email, name, gender, age.toInt(), weight.toFloat(), height.toFloat(), activeLevel).observe(this) { result ->
                                    if (result != null) {
                                        when (result) {
                                            is Result.Loading -> binding.pbLoading.visibility = View.VISIBLE
                                            is Result.Success -> {
                                                binding.pbLoading.visibility = View.GONE
                                                Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(this, LoginActivity::class.java)
                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                                startActivity(intent)
                                                finish()
                                            }
                                            is Result.Error -> {
                                                Toast.makeText(this, R.string.register_fail, Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
}