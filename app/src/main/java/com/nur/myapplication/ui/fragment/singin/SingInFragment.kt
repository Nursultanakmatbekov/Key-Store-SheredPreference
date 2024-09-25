package com.nur.myapplication.ui.fragment.singin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nur.data.local.prefs.TokenPreferenceHelper
import com.nur.myapplication.R
import com.nur.myapplication.databinding.FragmentSingInBinding
import com.nur.myapplication.ui.fragment.singin.state.SignInIntent
import com.nur.myapplication.ui.fragment.singin.state.SingInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SingInFragment : Fragment(R.layout.fragment_sing_in) {

    private val binding: FragmentSingInBinding by viewBinding(FragmentSingInBinding::bind)
    private val viewModel: SingInViewModel by viewModels()

    @Inject
    lateinit var tokenPreferenceHelper: TokenPreferenceHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModel()

    }

    private fun setupListeners() {
        binding.btmSingin.setOnClickListener {
            if (validateInput()) {
                viewModel.send(
                    SignInIntent.Submit(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (binding.etEmail.text.trim().isEmpty()) {
            binding.etEmail.error = "Введите email"
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        if (binding.etPassword.text.trim().isEmpty()) {
            binding.etPassword.error = "Введите пароль"
            isValid = false
        } else {
            binding.etPassword.error = null
        }

        return isValid
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.signInState.collect { state ->
                when (state) {
                    is SingInState.Idle -> {}

                    is SingInState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is SingInState.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val loginResponse = state.loginResponse
                        if (loginResponse.isSuccess) {
                            loginResponse.map {

                                tokenPreferenceHelper.authIsShown = true
                                findNavController().navigate(R.id.action_singInFragment_to_homeFragment)
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Неверный пароль или email",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

                    is SingInState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
