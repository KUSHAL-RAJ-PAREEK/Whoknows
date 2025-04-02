package com.krp.whoknows.Services

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.compose.animation.core.Transition
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.MainActivity
import com.krp.whoknows.R
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.roomdb.entity.FcmEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.getValue

/**
 * Created by Kushal Raj Pareek on 02-04-2025 00:56
 */


class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val ktorClient: KtorClient by inject()
    private val greetingViewModel: GreetingViewModel by inject()
    private val profileDetailViewModel: ProfileDetailViewModel by inject()


    companion object {
        var ringtone: Ringtone? = null
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCMssssssssssssssssssssssssssss", "$token")
        GlobalScope.launch {
            try {
                greetingViewModel.saveFcm(FcmEntity(id = 1, fcm_token =  token))
                profileDetailViewModel.updateFcm(token)
            } catch (e: Exception) {
                Log.e("FCM", "Error uploading token: ${e.message}")
            }
        }
        Log.d("FCM", "Token: $token")
    }

    @SuppressLint("ServiceCast")
    private fun isAppInForeground(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return appProcess.processName == packageName
            }
        }
        return false
    }



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (isAppInForeground()) {
            Log.d("FCM", "App is in foreground, notification data: ${remoteMessage.data}")
        } else {
            remoteMessage.data?.let {
                val type = it["type"]
                val title = it["title"]
                val body = it["body"]
                val img = it["imgUrl"]

                Log.d("zasfknajfshajsfhkjabsfkjabskjfbajkf","$type $title $img")
                if (type == "chat") {
                    if(img != "no"){
                        ImageNotification(context = this, title =  title, body = body, imageUrl = img)
                    }else{
                        showNotification(title, body)
                    }
                } else if (type == "buzz") {
                    showNotificationWithAlarm(this,title, body)
                }
            }
        }
    }



    @SuppressLint("LaunchActivityFromNotification")
    private fun showNotificationWithAlarm(context : Context, title: String?, body: String?) {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,  PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "KRP")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.fulll_handshake)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(1, notification)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, alarmSound)
        ringtone?.play()
    }

    class NotificationReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            ringtone?.stop()
        }
    }

    @SuppressLint("ServiceCast")
    private fun showNotification(title: String?, body: String?) {
        val channelId = "KRP"
        val channelName = "Whoknows"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.heart)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(0, notification)
    }
}


private fun ImageNotification(context: Context, title: String?, body: String?, imageUrl: String?) {
    val channelId = "KRP"
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
         "KRP",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300)
        }
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(body)
        .setSmallIcon(R.drawable.heart)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setAutoCancel(true)

    if (!imageUrl.isNullOrEmpty()) {
        Thread {
            val bitmap = getBitmapFromUrl(imageUrl)

            if (bitmap != null) {
                builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(bitmap))
                    .setSmallIcon(R.drawable.heart)
            }
            notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
        }.start()
    } else {
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}

private fun getBitmapFromUrl(imageUrl: String): Bitmap? {
    return try {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val inputStream: InputStream = connection.inputStream
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        Log.e("Notification", "Error fetching image: ${e.localizedMessage}")
        null
    }
}


