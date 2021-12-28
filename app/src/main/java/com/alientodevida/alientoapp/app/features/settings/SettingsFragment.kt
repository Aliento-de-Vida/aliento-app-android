package com.alientodevida.alientoapp.app.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alientodevida.alientoapp.app.databinding.FragmentSettingsBinding
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var preferences: Preferences

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

            preferences.isDarkThemeLive.observe(viewLifecycleOwner) { isDarkTheme ->
                isDarkTheme?.let { themeSwitch.isChecked = it }
            }

            themeSwitch.setOnCheckedChangeListener { _, checked ->
                preferences.isDarkTheme = checked
            }
        }
    }

    private fun observe() {
        preferences.nightModeLive.observe(viewLifecycleOwner) { nightMode ->
            nightMode?.let {
                (activity as AppCompatActivity).delegate.localNightMode = it
            }
        }
    }
}

























