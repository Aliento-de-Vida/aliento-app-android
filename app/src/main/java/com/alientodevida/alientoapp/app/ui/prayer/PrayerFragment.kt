package com.alientodevida.alientoapp.app.ui.prayer

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
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
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.databinding.FragmentPrayerBinding
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.network.base.ResponseError
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

        return binding.root
    }

    private fun setupUI(binding: FragmentPrayerBinding) {
        with(binding) {
            toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

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

            fabSend.setOnClickListener {
                this@PrayerFragment.viewModel.sendPrayerRequest()
            }
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
                    is ResponseError.ApiResponseError<*> -> Toast.makeText(requireContext(), "ApiError", Toast.LENGTH_SHORT).show()
                    is ResponseError.NetworkResponseError -> Toast.makeText(requireContext(), "NetworkError", Toast.LENGTH_SHORT).show()
                    is ResponseError.UnknownResponseError -> Toast.makeText(requireContext(), "UnknownError", Toast.LENGTH_SHORT).show()
                }
                viewModel.errorHandled()
            }
        }

	    viewModel.sendEmail.observe(owner = viewLifecycleOwner) {
		    it?.let {
			    viewModel.emailSent()
			    sendEmail(it.first, it.second)
		    }
	    }
    }

	private fun sendEmail(subject: String, message: String) {
		val emailIntent = Intent(Intent.ACTION_SEND)

		emailIntent.data = Uri.parse("mailto:")
		emailIntent.type = "text/plain"
		emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.PRAYER_EMAIL))
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
		emailIntent.putExtra(Intent.EXTRA_TEXT, message)

		try {
			startActivity(Intent.createChooser(emailIntent, "Send mail..."))
		} catch (ex: ActivityNotFoundException) {
			Utils.showDialog(requireContext(), "Lo sentimos", "Ha habido un error, por favor intente más tarde")
		}
	}
}

























