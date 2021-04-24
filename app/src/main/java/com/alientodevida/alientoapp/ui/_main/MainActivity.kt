package com.alientodevida.alientoapp.ui._main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.repository.PreferenceRepository
import com.alientodevida.alientoapp.databinding.ActivityMainBinding
import com.alientodevida.alientoapp.databinding.ToolbarBinding
import com.alientodevida.alientoapp.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding = ActivityMainBinding.inflate(layoutInflater)

        observe()

        setContentView(binding.root)
    }

    private fun observe() {
        preferenceRepository.nightModeLive.observe(this) { nightMode ->
            nightMode?.let { delegate.localNightMode = it }
        }
    }
}