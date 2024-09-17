package com.nur.myapplication.ui.fragment.blank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.nur.myapplication.R
import com.nur.myapplication.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : Fragment() {

    private val binding by viewBinding(FragmentBlankBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSnackbar.setOnClickListener {
            binding.tvOk.text = "OK"
            Snackbar.make(binding.root, "OK", Snackbar.LENGTH_LONG).show()
        }
        binding.btnChangeText.setOnClickListener {
            binding.tvText.text = "Hello"
        }
        binding.btnNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_homeFragment)
        }
    }
}