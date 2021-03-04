package com.alientodevida.alientoapp.ui.prayer

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.FragmentPrayerBinding
import com.alientodevida.alientoapp.utils.Utils

class PrayerFragment : Fragment() {

    private val viewModel by viewModels<PrayerViewModel>()

    companion object {
        fun newInstance() = PrayerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentPrayerBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        viewModel.isDataValid.observe(viewLifecycleOwner) {
            binding.fabSend.isEnabled = it
            binding.fabSend.backgroundTintList = ColorStateList.valueOf(if (it) Color.parseColor("#00B8D4") else Color.parseColor("#aaaaaa"))
        }

        viewModel.messageToShow.observe(viewLifecycleOwner) {
            Utils.showDialog(requireContext(), it.first, it.second)
        }

        binding.fabSend.setOnClickListener {
            viewModel.sendPrayerRequest()
        }

        return binding.root
    }

    private fun setupUI(binding: FragmentPrayerBinding) {
        binding.spinnerTopic.onItemSelectedListener = object: AdapterView.OnItemSelectedListener  {
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                viewModel.selectedTopic = if (position != 0) {
                    viewModel.topics[position]
                } else {
                    null
                }
                viewModel.validation()
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }

        val topicsAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, viewModel.topics)
        topicsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTopic.adapter = topicsAdapter


        binding.name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.name = binding.name.text.toString()
                viewModel.validation()
            }
        })

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.email = binding.email.text.toString()
                viewModel.validation()
            }
        })

        binding.whatsapp.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.whatsapp = binding.whatsapp.text.toString()
                viewModel.validation()
            }
        })

        binding.message.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.message = binding.message.text.toString()
                viewModel.validation()
            }
        })
    }
}

























