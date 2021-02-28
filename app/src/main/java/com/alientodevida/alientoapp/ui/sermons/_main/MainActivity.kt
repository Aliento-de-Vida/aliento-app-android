package com.alientodevida.alientoapp.ui.sermons._main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarView.icSettings.setOnClickListener {
            showUnderDevelopment()
        }
    }

    private fun showUnderDevelopment() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.under_development)
            .setPositiveButton(R.string.ok) { _, _ -> }
        builder.create().show()
    }
}