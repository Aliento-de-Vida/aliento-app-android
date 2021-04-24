package com.alientodevida.alientoapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.data.repository.PreferenceRepository
import com.alientodevida.alientoapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner

        setupUI(binding)
        observe()

        return binding.root
    }

    private fun setupUI(binding: FragmentSettingsBinding) {
        with(binding) {

            toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

            preferenceRepository.isDarkThemeLive.observe(viewLifecycleOwner) { isDarkTheme ->
                theme.text = if (isDarkTheme) "Dark Theme" else "Light Theme"
                isDarkTheme?.let { themeSwitch.isChecked = it }
            }

            themeSwitch.setOnCheckedChangeListener { _, checked ->
                preferenceRepository.isDarkTheme = checked
            }
        }
    }

    private fun observe() {
        preferenceRepository.nightModeLive.observe(viewLifecycleOwner) { nightMode ->
            nightMode?.let {
                (activity as AppCompatActivity).delegate.localNightMode = it
            }
        }
    }
}

























