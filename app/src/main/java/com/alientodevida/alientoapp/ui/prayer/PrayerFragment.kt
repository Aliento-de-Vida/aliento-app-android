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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.network.base.ResponseError
import com.alientodevida.alientoapp.databinding.FragmentPrayerBinding
import com.alientodevida.alientoapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerFragment : Fragment() {

    private val viewModel by viewModels<PrayerViewModel>()

    companion object {
        fun newInstance() = PrayerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentPrayerBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)
        observeViewModel(binding)

        binding.fabSend.setOnClickListener {
            viewModel.sendPrayerRequest()
        }

        return binding.root
    }

    private fun setupUI(binding: FragmentPrayerBinding) {
        with(binding) {
            spinnerTopic.onItemSelectedListener = object: AdapterView.OnItemSelectedListener  {
                override fun onItemSelected(arg0: AdapterView<*>, arg1: View?, position: Int, id: Long) {
                    this@PrayerFragment.viewModel.selectedTopic = if (position != 0) {
                        this@PrayerFragment.viewModel.topics[position]
                    } else {
                        null
                    }
                    this@PrayerFragment.viewModel.validation()
                }
                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }

            val topicsAdapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_simple_item,
                this@PrayerFragment.viewModel.topics
            )
            topicsAdapter.setDropDownViewResource(R.layout.spinner_simple_item)
            spinnerTopic.adapter = topicsAdapter


            name.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.name = name.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            email.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.email = email.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            whatsapp.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.whatsapp = binding.whatsapp.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            message.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.message = message.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })
        }
    }

    private fun observeViewModel(binding: FragmentPrayerBinding) {
        viewModel.isDataValid.observe(owner = viewLifecycleOwner) {
            binding.fabSend.isEnabled = it
            binding.fabSend.backgroundTintList = ColorStateList.valueOf(if (it) Color.parseColor("#00B8D4") else Color.parseColor("#aaaaaa"))
        }

        viewModel.messageToShow.observe(owner = viewLifecycleOwner) {
            Utils.showDialog(requireContext(), it.first, it.second)
        }

        viewModel.onError.observe(owner = viewLifecycleOwner) { onError ->
            onError?.let {
                when(onError.result) {
                    is ResponseError.ApiResponseError -> Toast.makeText(requireContext(), "ApiError", Toast.LENGTH_SHORT).show()
                    is ResponseError.NetworkResponseError -> Toast.makeText(requireContext(), "NetworkError", Toast.LENGTH_SHORT).show()
                    is ResponseError.UnknownResponseError -> Toast.makeText(requireContext(), "UnknownError", Toast.LENGTH_SHORT).show()
                }
                viewModel.errorHandled()
            }
        }
    }
}

























