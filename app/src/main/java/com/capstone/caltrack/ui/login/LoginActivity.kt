package com.capstone.caltrack.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.caltrack.R
import com.capstone.caltrack.data.Result
import com.capstone.caltrack.databinding.ActivityLoginBinding
import com.capstone.caltrack.ui.ViewModelFactory
import com.capstone.caltrack.ui.main.MainActivity
import com.capstone.caltrack.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setUpViewModel()
        setUpAction()
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    private fun setUpAction() {
        binding.goToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmail.error = getString(R.string.input_email)
                }
                password.isEmpty() -> {
                    binding.etPassword.error = getString(R.string.input_password)
                }
                else -> {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                                loginViewModel.login(email).observe(this){ result ->
                                    if (result != null) {
                                        when (result) {
                                            is Result.Loading-> binding.pbLoading.visibility = View.VISIBLE
                                            is Result.Success -> {
                                                binding.pbLoading.visibility = View.GONE
                                                val intent = Intent(this, MainActivity::class.java)
                                                intent.flags =
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                                startActivity(intent)
                                                finish()
                                            }
                                            is Result.Error -> {
                                                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT)
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