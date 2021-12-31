package com.alientodevida.alientoapp.app.messaging

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.alientodevida.alientoapp.app.MainActivity
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.features.notifications.detail.NotificationDetailFragmentArgs
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.extensions.notEmptyOrNull
import com.alientodevida.alientoapp.domain.home.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
  
  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    remoteMessage.notification?.let {
      val image = remoteMessage.data.notEmptyOrNull()?.get("image")?.notEmptyOrNull()
      val date = remoteMessage.data.notEmptyOrNull()?.get("date")?.notEmptyOrNull()
      var notification: Notification? = null
      
      if (image != null && date != null) {
        notification = Notification(
          0,
          it.title ?: "",
          it.body ?: "",
          Image(image),
          date,
        )
      }
      
      
      sendNotification(it.title ?: "", it.body ?: "", notification)
    }
  }
  
  @SuppressLint("UnspecifiedImmutableFlag")
  private fun sendNotification(title: String, body: String, notification: Notification?) {
    val pendingIntent = notification?.let {
      NavDeepLinkBuilder(this)
        .setGraph(R.navigation.splashscreen_navigation)
        .setDestination(R.id.fragment_notification_detail)
        .setArguments(NotificationDetailFragmentArgs(notification).toBundle())
        .createPendingIntent()
    } ?: run {
      PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java),
        PendingIntent.FLAG_ONE_SHOT
      )
    }
    
    val channelId = "aliento_channel"
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)
      .setSmallIcon(R.drawable.ic_logo_round)
      .setContentTitle(title)
      .setContentText(body)
      .setAutoCancel(true)
      .setSound(defaultSoundUri)
      .setContentIntent(pendingIntent)
    
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId,
        "Notificationes generales",
        NotificationManager.IMPORTANCE_DEFAULT
      )
      notificationManager.createNotificationChannel(channel)
    }
    
    notificationManager.notify(0, notificationBuilder.build())
  }
  
  override fun onNewToken(token: String) {
    Log.d(TAG, "Refreshed token: $token")
  }
  
  companion object {
    private const val TAG = "MyFirebaseMsgService"
  }
  
}