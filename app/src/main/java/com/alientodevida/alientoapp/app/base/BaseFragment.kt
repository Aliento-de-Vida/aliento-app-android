package com.alientodevida.alientoapp.app.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.state.ViewModelResult

abstract class BaseFragment<VDB : ViewDataBinding>(
  @LayoutRes protected val layoutId: Int,
) : Fragment() {
  
  protected lateinit var binding: VDB
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }
  
  protected fun <T> viewModelResult(
    result: ViewModelResult<T>,
    progressBar: ProgressBar? = null,
    onError: () -> Unit = { },
    onSuccess: (T) -> Unit,
  ) {
    progressBar?.isVisible = result is ViewModelResult.Loading
    
    when (result) {
      is ViewModelResult.Success -> {
        onSuccess(result.data)
      }
      is ViewModelResult.Error -> {
        onError()
        showToast(result.message)
      }
    }
  }
  
  protected fun showMessage(titleRes: Int, messageRes: Int, onClick: () -> Unit) {
    val title = getString(titleRes)
    val message = getString(messageRes)
    showMessage(
      title = title,
      message = message,
      onClick = onClick
    )
  }
  
  protected fun showMessage(
    title: String,
    message: String,
    positiveTitle: Int = R.string.ok,
    onClick: () -> Unit = { }
  ) {
    createAlertDialog(title, message, positiveTitle, onClick)
  }
  
  private fun createAlertDialog(
    title: String,
    message: String,
    positiveTitle: Int,
    onPositiveClick: () -> Unit,
  ) {
    val builder = AlertDialog.Builder(requireActivity())
    builder
      .setTitle(title)
      .setMessage(message)
      .setPositiveButton(positiveTitle) { _, _ ->
        onPositiveClick()
      }.create().show()
  }
  
  fun showToast(message: Message) {
    Toast.makeText(
      requireContext(),
      when (message) {
        is Message.Resource -> getString(message.message)
        is Message.Localized -> message.message
      },
      Toast.LENGTH_LONG
    ).show()
  }
}