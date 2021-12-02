package com.alientodevida.alientoapp.app.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.alientodevida.alientoapp.app.BuildConfig
import com.alientodevida.alientoapp.app.R
import com.google.android.youtube.player.YouTubeStandalonePlayer
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {

        fun goToUrl(context: Context, webPageUrl: String) {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webPageUrl))
            context.startActivity(openBrowserIntent)
        }

        fun handleOnClick(activity: Activity, videoId: String) {
            val intent: Intent = YouTubeStandalonePlayer.createVideoIntent(
                activity,
                BuildConfig.YOUTUBE_DEVELOPER_KEY,
                videoId
            )
            activity.startActivity(intent)
        }

        fun copyToClipboard(
            context: Context,
            name: String,
            value: String
        ) {
            val myClipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("clipboard", value)
            myClipboard.setPrimaryClip(myClip)
            context
            Toast.makeText(context, "$name copiado", Toast.LENGTH_SHORT).show()
        }

        fun showComingSoon(context: Context) {
            showDialog(
                context,
                context.getString(R.string.comming_soon_title),
                context.getString(R.string.comming_soon_message)
            )
        }

        fun showDialog(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok) { _, _ -> }
            builder.create().show()
        }

        fun dateFrom(date: String, format: String = "yyyy-MM-dd HH:mm:ss"): Date {
            return SimpleDateFormat(format, Locale.US).parse(date)!!
        }

        fun format(date: Date, format: String): String {
            return SimpleDateFormat(format, Locale("es", "ES")).format(date.time)
        }

    }
}