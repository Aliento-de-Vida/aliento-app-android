package com.alientodevida.alientoapp.app.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.alientodevida.alientoapp.app.MainActivity
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.extensions.notEmptyOrNull
import com.alientodevida.alientoapp.domain.notification.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
  
  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    val id = remoteMessage.data.notEmptyOrNull()?.get("id")?.notEmptyOrNull()
    val title = remoteMessage.data.notEmptyOrNull()?.get("title")?.notEmptyOrNull()
    val body = remoteMessage.data.notEmptyOrNull()?.get("body")?.notEmptyOrNull()
    val image = remoteMessage.data.notEmptyOrNull()?.get("image")?.notEmptyOrNull()
    val date = remoteMessage.data.notEmptyOrNull()?.get("date")?.notEmptyOrNull()
    var notification: Notification? = null
    
    if (title != null && body != null && id != null) {
      if (image != null && date != null)
        notification = Notification(id.toInt(), title, body, Image(image), date)
      
      sendNotification(title, body, notification)
    }
  }
  
  private fun sendNotification(title: String, body: String, notification: Notification?) {
    val pendingIntent = notification?.let {
      val context = applicationContext
      val deepLinkIntent = Intent(
        Intent.ACTION_VIEW,
        "https://todoserver-peter.herokuapp.com/${it.id}".toUri(),
        context,
        MainActivity::class.java
      )
  
      TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(deepLinkIntent)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
          PendingIntent.FLAG_UPDATE_CURRENT
        }
        getPendingIntent(0, flags)
      }
    } ?: run {
      PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java),
        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE,
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