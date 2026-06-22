package com.legaldocai.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.legaldocai.databinding.FragmentSettingsBinding
import com.legaldocai.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.apiKey.observe(viewLifecycleOwner) { key ->
            if (key.isNotEmpty()) {
                binding.etApiKey.setText(key)
            }
        }
        viewModel.language.observe(viewLifecycleOwner) { lang ->
            binding.spinnerLanguage.setSelection(
                resources.getStringArray(com.legaldocai.R.array.languages).indexOfFirst { it == lang }.coerceAtLeast(0)
            )
        }
    }

    private fun setupClickListeners() {
        binding.btnSaveApiKey.setOnClickListener {
            val key = binding.etApiKey.text.toString().trim()
            if (key.isNotEmpty()) {
                viewModel.saveApiKey(key)
                Toast.makeText(requireContext(), "API Key saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a valid API key", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSaveLanguage.setOnClickListener {
            val selectedLang = binding.spinnerLanguage.selectedItem.toString()
            viewModel.saveLanguage(selectedLang)
            Toast.makeText(requireContext(), "Language preference saved!", Toast.LENGTH_SHORT).show()
        }

        binding.switchVoiceFeedback.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setVoiceFeedback(isChecked)
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkMode(isChecked, requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
