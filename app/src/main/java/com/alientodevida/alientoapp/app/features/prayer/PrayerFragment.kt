package com.alientodevida.alientoapp.app.features.prayer

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.features.notifications.list.Notifications
import com.alientodevida.alientoapp.app.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<PrayerViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Prayer(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
          )
        }
      }
    }
  }
  
}

fun Context.sendEmail(to: String, subject: String, message: String) {
  val emailIntent = Intent(Intent.ACTION_SEND)
  
  emailIntent.data = Uri.parse("mailto:")
  emailIntent.type = "text/plain"
  emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
  emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
  emailIntent.putExtra(Intent.EXTRA_TEXT, message)
  
  try {
    startActivity(Intent.createChooser(emailIntent, "Send mail..."))
  } catch (ex: ActivityNotFoundException) {
    Utils.showDialog(
      this,
      "Lo sentimos",
      "Ha habido un error, por favor intente más tarde"
    )
  }
}

























