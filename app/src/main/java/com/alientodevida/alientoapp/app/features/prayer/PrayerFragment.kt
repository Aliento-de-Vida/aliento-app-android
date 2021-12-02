package com.alientodevida.alientoapp.app.features.prayer

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentPrayerBinding
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.sendEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerFragment : BaseFragment<FragmentPrayerBinding>(R.layout.fragment_prayer) {

    private val viewModel by viewModels<PrayerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(binding)
        observeViewModel(binding)
    }

    private fun setupUI(binding: FragmentPrayerBinding) {
        with(binding) {
            toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

            spinnerTopic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    arg0: AdapterView<*>,
                    arg1: View?,
                    position: Int,
                    id: Long
                ) {
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
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.name = name.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            email.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.email = email.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            whatsapp.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.whatsapp = binding.whatsapp.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            message.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    this@PrayerFragment.viewModel.message = message.text.toString()
                    this@PrayerFragment.viewModel.validation()
                }
            })

            fabSend.setOnClickListener {
                this@PrayerFragment.viewModel.sendPrayerRequest()
            }
        }
    }

    private fun observeViewModel(binding: FragmentPrayerBinding) {
        viewModel.isDataValid.observe(viewLifecycleOwner) {
            binding.fabSend.isEnabled = it
            binding.fabSend.backgroundTintList =
                ColorStateList.valueOf(if (it) Color.parseColor("#00B8D4") else Color.parseColor("#aaaaaa"))
        }

        viewModel.messageToShow.observe(viewLifecycleOwner) {
            Utils.showDialog(requireContext(), it.first, it.second)
        }

        viewModel.sendEmail.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.emailSent()
                sendEmail(it.first, it.second)
            }
        }
    }

    private fun sendEmail(subject: String, message: String) {
        viewModel.home?.prayerEmail?.let {
            requireActivity().sendEmail(it, subject, message)
        }
    }
}

























