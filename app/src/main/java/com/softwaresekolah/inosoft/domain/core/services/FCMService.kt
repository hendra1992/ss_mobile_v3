package com.softwaresekolah.inosoft.domain.core.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.presentation.core.MainActivity.MainActivity
import timber.log.Timber

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.tag(TAG).d( "From: ${remoteMessage.from}")

        lateinit var  title: String
        lateinit var body: String
        lateinit var idSiswa: String

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Timber.tag(TAG).d("Message data payload: ${remoteMessage.data}")
            title = remoteMessage.data["title"].toString()
            body = remoteMessage.data["body"].toString()
            idSiswa = remoteMessage.data["id_siswa"].toString()
            // Check if data needs to be processed by long running job
            if (true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob(applicationContext)
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
            sendNotification(title = title, messageBody = body, idSiswa = idSiswa)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Timber.tag(TAG).d("Message Notification Body: ${it.body}")
        }
  }

    override fun onNewToken(token: String) {
        Timber.tag(TAG).d("Refreshed token: %s", token)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob(context: Context) {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(context).beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Timber.tag(TAG).d("Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Timber.tag(TAG).d("sendRegistrationTokenToServer($token)")
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    @SuppressLint("ObsoleteSdkInt")
    private fun sendNotification(title: String, messageBody: String, idSiswa: String) {
        val deepLinkHost = R.string.deep_link_host
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://$deepLinkHost.com/notification/$idSiswa")
        )
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.default_notification_channel_name)
            val descriptionText = getString(R.string.default_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            mChannel.enableLights(true)
            mChannel.enableVibration(true)
            mChannel.shouldShowLights()
            mChannel.shouldVibrate()
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(mChannel)
        }
        val activity = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_login)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(activity)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


    companion object{
        const val TAG = "FCM-SERVICE"
    }
}