package com.alientodevida.alientoapp.ui.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.common.Notification
import com.alientodevida.alientoapp.domain.di.ActivityIntent
import com.alientodevida.alientoapp.domain.di.BaseUrl
import com.alientodevida.alientoapp.ui.R
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import android.app.Notification as AndroidNotification

private const val CHANNEL_ID = "aliento_channel"
const val PUSH_NOTIFICATIONS_TOPIC = "push_notifications"

@Singleton
@AndroidEntryPoint
class NotificationsManager @Inject constructor() : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject
    @BaseUrl
    lateinit var baseUrl: String

    @Inject
    lateinit var logger: Logger

    @Inject
    @ActivityIntent
    lateinit var activityIntent: Class<*>

    fun subscribeToPushNotifications(subscribe: Boolean) {
        if (subscribe) {
            firebaseMessaging.subscribeToTopic(PUSH_NOTIFICATIONS_TOPIC)
        } else {
            firebaseMessaging.unsubscribeFromTopic(PUSH_NOTIFICATIONS_TOPIC)
        }
    }

    override fun onNewToken(token: String) {
        logger.d("Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val id = remoteMessage.data["id"] ?: return
        val title = remoteMessage.data["title"] ?: return
        val body = remoteMessage.data["body"] ?: return

        val image = remoteMessage.data["image"]
        val date = remoteMessage.data["date"]
        var notification: Notification? = null

        if (image != null && date != null) {
            notification =
                Notification(id.toInt(), title, body, Image(image), date)
        }

        sendNotification(title, body, notification)
    }

    private fun sendNotification(
        title: String,
        body: String,
        notification: Notification?,
    ) {
        val pendingIntent = if (notification != null) {
            notification.deepLinkIntent().pendingIntent()
        } else {
            context.appIntent()
        }

        val androidNotification = getNotification(title, body, pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.addChannel()
        notificationManager.notify(0, androidNotification)
    }

    private fun Context.appIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, activityIntent),
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_ONE_SHOT
        },
    )

    private fun Notification.deepLinkIntent() = Intent(
        Intent.ACTION_VIEW,
        "$baseUrl/$id".toUri(),
        context,
        activityIntent,
    )

    private fun Intent.pendingIntent(): PendingIntent? = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(this@pendingIntent)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        getPendingIntent(0, flags)
    }

    private fun getNotification(
        title: String,
        body: String,
        pendingIntent: PendingIntent?,
    ): AndroidNotification {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo_round)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun NotificationManager.addChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificationes generales",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            createNotificationChannel(channel)
        }
    }
}
